package com.info.dragdropcoklunesne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private LinearLayout ust_tasarim, sol_tasarim, sag_tasarim;
    private Button button;
    private TextView textView;
    private ImageView imageView;

    private static final String TEXT_ETIKET = "YAZI";
    private static final String BUTTON_ETIKET = "BUTON";
    private static final String RESIM_ETIKET = "RESIM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        ust_tasarim = findViewById(R.id.ust_tasarim);
        sol_tasarim = findViewById(R.id.sol_tasarim);
        sag_tasarim = findViewById(R.id.sag_tasarim);

        textView.setTag(TEXT_ETIKET);
        button.setTag(BUTTON_ETIKET);
        imageView.setTag(RESIM_ETIKET);

        imageView.setOnLongClickListener(this);
        button.setOnLongClickListener(this);
        textView.setOnLongClickListener(this);

        ust_tasarim.setOnDragListener(this);
        sol_tasarim.setOnDragListener(this);
        sag_tasarim.setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                view.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                view.getBackground().clearColorFilter();
                view.invalidate();

                View gorselNesne = (View) dragEvent.getLocalState();
                ViewGroup eskiTasarimAlani = (ViewGroup) gorselNesne.getParent();
                eskiTasarimAlani.removeView(gorselNesne);

                LinearLayout hedefTasarimAlani = (LinearLayout) view;
                hedefTasarimAlani.addView(gorselNesne);

                gorselNesne.setVisibility(View.VISIBLE);

                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onLongClick(View view) {

        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData clipData = new ClipData(view.getTag().toString(), mimeTypes, item);

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        view.startDrag(clipData, shadowBuilder, view, 0);

        view.setVisibility(View.INVISIBLE);

        return true;
    }
}