package com.google.appinventor.components.runtime;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import java.util.concurrent.atomic.AtomicInteger;

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
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private android.support.wearable.view.CardFragment mCardFragment;
    private String title;
    private String description = "..."; // TODO(richard) - take out
    private int iconResource = 0; // TODO(richard) - take out
    public final String TAG = "CardFragment";
    private Activity context;
    private Form form;
//    private android.support.wearable.view.WearableFrameLayout viewLayout;
//    private FrameLayout viewLayout;
    private android.widget.FrameLayout viewLayout;
    private FragmentManager fragmentManager;
    private final Handler androidUIHandler = new Handler();



    public CardFragment(ComponentContainer container) {
        super(container);
        mCardFragment = android.support.wearable.view.CardFragment.create(title, description);
        context = container.$context();
        form = container.$form();
        Log.i(TAG, "In the constructor of CardFragment.java");
        //viewLayout = new android.support.wearable.view.WearableFrameLayout(context);
        viewLayout = new android.widget.FrameLayout(context);
        viewLayout.setId(View.generateViewId());
        fragmentManager = context.getFragmentManager();

        FragmentTransaction fragmentTransaction = form.getFragmentManager().beginTransaction();
        fragmentTransaction.add(viewLayout.getId(), mCardFragment);
        fragmentTransaction.commit();

        // Adds the component to its designated container
        container.$add(this);

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

    /**
     * Generate a value suitable for use in .
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    private static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
