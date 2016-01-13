// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.MediaEntity;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;

/**
 * Component for accessing Twitter.
 *
 * @author sharon@google.com (Sharon Perl) - added OAuth support
 * @author ajcolter@gmail.com (Aubrey Colter) - added the twitter4j 2.2.6 jars
 * @author josmasflores@gmail.com (Jose Dominguez) - added the twitter4j 3.0.3 jars and fixed auth bug 2413
 * @author edwinhzhang@gmail.com (Edwin Zhang) - added twitter4j-media-support-3.03 jar, status + image upload
 */
@DesignerComponent(version = YaVersion.TWITTER_COMPONENT_VERSION, description = "A non-visible component that enables communication "
    + "with <a href=\"http://www.twitter.com\" target=\"_blank\">Twitter</a>. "
    + "Once a user has logged into their Twitter account (and the authorization has been confirmed successful by the "
    + "<code>IsAuthorized</code> event), many more operations are available:<ul>"
    + "<li> Searching Twitter for tweets or labels (<code>SearchTwitter</code>)</li>\n"
    + "<li> Sending a Tweet (<code>Tweet</code>)"
    + "     </li>\n"
    + "<li> Sending a Tweet with an Image (<code>TweetWithImage</code>)"
    + "     </li>\n"
    + "<li> Directing a message to a specific user "
    + "     (<code>DirectMessage</code>)</li>\n "
    + "<li> Receiving the most recent messages directed to the logged-in user "
    + "     (<code>RequestDirectMessages</code>)</li>\n "
    + "<li> Following a specific user (<code>Follow</code>)</li>\n"
    + "<li> Ceasing to follow a specific user (<code>StopFollowing</code>)</li>\n"
    + "<li> Getting a list of users following the logged-in user "
    + "     (<code>RequestFollowers</code>)</li>\n "
    + "<li> Getting the most recent messages of users followed by the "
    + "     logged-in user (<code>RequestFriendTimeline</code>)</li>\n "
    + "<li> Getting the most recent mentions of the logged-in user "
    + "     (<code>RequestMentions</code>)</li></ul></p>\n "
    + "<p>You must obtain a Consumer Key and Consumer Secret for Twitter authorization "
    + " specific to your app from http://twitter.com/oauth_clients/new",
    category = ComponentCategory.SOCIAL, nonVisible = true, iconName = "images/twitter.png")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
//@UsesLibraries(libraries = "twitter4j.jar," + "twitter4jmedia.jar")
public final class Twitter extends AndroidNonvisibleComponent {
  private static final String ACCESS_TOKEN_TAG = "TwitterOauthAccessToken";
  private static final String ACCESS_SECRET_TAG = "TwitterOauthAccessSecret";
  private static final String MAX_CHARACTERS = "160";
  private static final String URL_HOST = "twitter";
  private static final String CALLBACK_URL = Form.APPINVENTOR_URL_SCHEME
      + "://" + URL_HOST;
  private static final String WEBVIEW_ACTIVITY_CLASS = WebViewActivity.class
      .getName();

  // the following fields should only be accessed from the UI thread
  private String consumerKey = "";
  private String consumerSecret = "";
  private String TwitPic_API_Key = "";
  private final List<String> mentions;
  private final List<String> followers;
  private final List<List<String>> timeline;
  private final List<String> directMessages;
  private final List<String> searchResults;

  // the following final fields are not synchronized -- twitter4j is thread
  // safe as of 2.2.6
  private twitter4j.Twitter twitter;
  private RequestToken requestToken;
  private AccessToken accessToken;
  private String userName = "";
  private final SharedPreferences sharedPreferences;
  private final ComponentContainer container;
  private final Handler handler;
  
  // Logging
  private final String TAG = "Twitter";

  // TODO(sharon): twitter4j apparently has an asynchronous interface
  // (AsynchTwitter).
  // We should consider whether it has any advantages over AsynchUtil.

  /**
   * The maximum number of mentions returned by the following methods:
   *
   * <table>
   * <tr>
   * <td>component</td>
   * <td>twitter4j library</td>
   * <td>twitter API</td>
   * </tr>
   * <tr>
   * <td>RequestMentions</td>
   * <td>getMentions</td>
   * <td>statuses/mentions</td>
   * </tr>
   * <tr>
   * <td>RequestDirectMessages</td>
   * <td>getDirectMessages</td>
   * <td>direct_messages</td>
   * </tr>
   * </table>
   */
  private static final String MAX_MENTIONS_RETURNED = "20";

  public Twitter(ComponentContainer container) {
    super(container.$form());
    this.container = container;
    handler = new Handler();

    mentions = new ArrayList<String>();
    followers = new ArrayList<String>();
    timeline = new ArrayList<List<String>>();
    directMessages = new ArrayList<String>();
    searchResults = new ArrayList<String>();

    sharedPreferences = container.$context().getSharedPreferences("Twitter",
        Context.MODE_PRIVATE);

  }

}
