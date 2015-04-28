// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0
package com.google.appinventor.buildserver;

public class XmlDescConstants {

    private String packageName;
    private String versionCode;
    private String versionName;
    public XmlDescConstants(String packageName,String versionCode,String versionName) {
        this.packageName = packageName;

        this.versionCode = versionCode;

      this.versionName= versionName;

  }

    public  String getWearableDescXml(){
        String xmlDesc = "<wearableApp package='"+packageName+"'>";
        xmlDesc +=          "<versionCode>"+versionCode+"</versionCode>";
        xmlDesc +=          "<versionName>"+versionName+"</versionName>";
        xmlDesc +=          "<rawPathResId>wearable_app</rawPathResId></wearableApp>";
        return xmlDesc;
    }
}


