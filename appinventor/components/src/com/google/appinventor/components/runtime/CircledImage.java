// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import android.graphics.drawable.Drawable;
import android.support.wearable.view.CircledImageView;
import android.view.View;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import android.util.Log;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;

/**
 * Component for displaying circled images for Android Wear
 *@author Richard
 */
@DesignerComponent(version = YaVersion.CIRCLED_IMAGE_COMPONENT_VERSION,
        category = ComponentCategory.USERINTERFACE,
        description = "Component for displaying circled images.  The picture to display, " +
                "and other aspects of the Image's appearance, can be specified in the " +
                "Designer or in the Blocks Editor.")
@SimpleObject
@UsesLibraries(libraries = "android-support-wearable.jar")
public class CircledImage extends AndroidViewComponent {

    private final CircledImageView view;

    private String picturePath;  // Picture property
    private int radius;

    /**
     * Creates a new Image component.
     *
     * @param container  container, component will be placed in
     */
    public CircledImage(ComponentContainer container) {
        super(container);

        view = new CircledImageView(container.$context()) {
            @Override
            public boolean verifyDrawable(Drawable dr) {
                super.verifyDrawable(dr);
                // TODO(user): multi-image animation
                return true;
            }
        };
        picturePath = "";
        radius = Component.CIRCLED_IMAGE_VIEW_RADIUS;

        // Adds the component to its designated container
        container.$add(this);
        view.setFocusable(true);
    }

    @Override
    public View getView() {
        return view;
    }


    /**
     * Returns the path of the image's picture.
     *
     * @return  the path of the image's picture
     */
    @SimpleProperty(
            category = PropertyCategory.APPEARANCE)
    public String Picture() {
        return picturePath;
    }

    /**
     * Specifies the path of the image's picture.
     *
     * <p/>See {@link MediaUtil#determineMediaSource} for information about what
     * a path can be.
     *
     * @param path  the path of the image's picture
     */
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET,
            defaultValue = "")
    @SimpleProperty
    public void Picture(String path) {
        picturePath = (path == null) ? "" : path;

        Drawable drawable;
        try {
            drawable = MediaUtil.getBitmapDrawable(container.$form(), picturePath);
        } catch (IOException ioe) {
            Log.e("Image", "Unable to load " + picturePath);
            drawable = null;
        }

        ViewUtil.setImage(view, drawable);
    }


    /**
     * Returns the radius of this CircledImageView
     */
    @SimpleProperty(
            description = "The radius of this CircledImageView"
    )
    public int Radius() {
        return this.radius;
    }

    /**
     * Sets the radius of this CircledImageView
     */
    @SimpleProperty(
            description = "Sets the radius of this CircledImageView"
    )
    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER,
            defaultValue = Component.CIRCLED_IMAGE_VIEW_RADIUS + ""
    )
    public void Radius(int radius){
        this.radius = radius;
    }
}
