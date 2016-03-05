package com.google.appinventor.components.runtime;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;

/**
 * Component for constructing a CardFragment for Android Wear
 * @author Richard
 */
@DesignerComponent(version = YaVersion.CARD_FRAGMENT_COMPONENT_VERSION,
        category = ComponentCategory.USERINTERFACE,
        description = "to be written...")
@SimpleObject
@UsesLibraries(libraries = "android-support-wearable.jar")
public class CardFragment extends AndroidViewComponent{

    private android.support.wearable.view.CardFragment cardFragmentObject;
    private String title;
    private String description = "..."; // TODO(richard) - take out
    private int iconResource = 0; // TODO(richard) - take out
    public final String TAG = "CardFragment";
    private final Activity context;
    private android.widget.FrameLayout viewLayout;
    private FragmentManager fragmentManager;
    private final Handler androidUIHandler = new Handler();



    public CardFragment(ComponentContainer container) {
        super(container);
        cardFragmentObject = android.support.wearable.view.CardFragment.create(title, description);
        context = container.$context();
        Log.i(TAG, "In the constructor of CardFragment.java");
        viewLayout = new android.widget.FrameLayout(context);
        fragmentManager = context.getFragmentManager();

        androidUIHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean dispatchEventNow = false;
                if (viewLayout != null){
                    dispatchEventNow = true;
                }
                if (dispatchEventNow){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(viewLayout.getId(), cardFragmentObject);
                    fragmentTransaction.commit();
                } else {
                    // try again later
                    androidUIHandler.post(this);
                }
            }
        });


        // TODO(richard) - use setArgument to dynamically change the title, desc, and icon based on Punya user input


    }

    @Override
    public View getView() {
        return viewLayout;
    }

    /**
     * Returns the title
     */
    @SimpleProperty(
            category = PropertyCategory.APPEARANCE,
            description = "The title of the card"
    )
    public String CardTitle() {
        return this.title;
    }

    /**
     * Sets the title of the card
     */
    @SimpleProperty(
            description = "Sets the title of the card"
    )
    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER,
            defaultValue = Component.CARD_FRAGMENT_TITLE
    )
    public void CardTitle(String title) {
        this.title = title;
        updateAppearance();
    }

    private void updateAppearance() {
        // to be written ...
    }
}
