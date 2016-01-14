// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.editor.simple.SimpleEditor;

/**
 * Mock Image component.
 * @author Richard
 */
public final class MockCircledImage extends MockImageBase {

    public static final String TYPE = "CircledImage";

    // GWT widget used to mock a Simple Button
    private int radius;

    /**
     * Creates a new MockImage component.
     *
     * @param editor  editor of source file the component belongs to
     */
    public MockCircledImage(SimpleEditor editor) {
        super(editor, TYPE, images.image());
    }

    private void setRadiusProperty(String text){
        this.radius = Integer.parseInt(text);
        // do something...
        // consider changing the width and the height simultaneously

    }


    @Override
    public void onPropertyChange(String propertyName, String newValue) {
        super.onPropertyChange(propertyName, newValue);

        // Apply changed properties to the mock component
        if (propertyName.equals(PROPERTY_NAME_CIRCLEDIMAGERADIUS)){
            super.onPropertyChange(PROPERTY_NAME_WIDTH, newValue);
            super.onPropertyChange(PROPERTY_NAME_HEIGHT, newValue);
            setRadiusProperty(newValue);
            refreshForm();
        }
    }

}
