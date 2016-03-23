package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.editor.simple.SimpleEditor;

/**
 * Created by Richard on 3/5/16.
 */
public class MockCardFragment extends MockImageBase{
    public static final String TYPE = "CardFragment";
    public MockCardFragment(SimpleEditor editor) {
        super(editor, TYPE, images.image());
    }


    @Override
    public void onPropertyChange(String propertyName, String newValue) {
        super.onPropertyChange(propertyName, newValue);

        // Apply changed properties to the mock component
        if (propertyName.equals(PROPERTY_NAME_CIRCLEDIMAGERADIUS)) {
            //super.onPropertyChange(PROPERTY_NAME_WIDTH, newValue);
            //super.onPropertyChange(PROPERTY_NAME_HEIGHT, newValue);
            refreshForm();
        }

        if (propertyName.equals(PROPERTY_NAME_CIRCLEDIMAGEBORDERCOLOR)) {
            refreshForm();
        }
        if (propertyName.equals(PROPERTY_NAME_CIRCLEDIMAGECIRCLECOLOR)) {
            refreshForm();
        }
        if (propertyName.equals(PROPERTY_NAME_CIRCLEDIMAGEBORDERWIDTH)) {
            refreshForm();
        }
    }
}
