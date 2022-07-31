package com.jabirdeveloper.tinderswipe;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String nama;
    int count = 0;
    private static final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    count++;
                    Toast.makeText(MainActivity.this, "Like", Toast.LENGTH_SHORT).show();

                }
                if (direction == Direction.Top){
                    Toast.makeText(MainActivity.this, "Direction Top", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left){
                    count++;
                    Toast.makeText(MainActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom){
                    Toast.makeText(MainActivity.this, "Direction Bottom", Toast.LENGTH_SHORT).show();
                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(false);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
        Button hyperlink = findViewById(R.id.hyperlink);
        hyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ItemModel> baru = new ArrayList<>(addList());
                nama = baru.get(count).getNama();
                browser(nama);
            }
        });

    }
    public void browser(String name)
    {
        Intent browserIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse(sites(name)));
        startActivity(browserIntent);
    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.sample1, "OPPO F15", "OPPO", "CHINA"));
        items.add(new ItemModel(R.drawable.oneplus_7t_1569568197150, "ONEPLUS 7T", "ONEPLUS", "CHINA"));
        items.add(new ItemModel(R.drawable.xiaomi_poco_x3_pro_1, "POCO X3 PRO", "XIAOMI", "CHINA"));
        items.add(new ItemModel(R.drawable.vivo_v23_pro_r1, "Vivo V23 5G", "VIVO", "CHINA"));
        items.add(new ItemModel(R.drawable.motorola_moto_g71_5g_neptune_green_1, "Motorola Moto G71 5G", "LENOVO", "UNITED STATES"));
        items.add(new ItemModel(R.drawable.samsung_galaxy_s20_fe_5g_13_1280x720, "Samsung Galaxy S20 FE 5G", "SAMSUNG", "SOUTH KOREA"));
        items.add(new ItemModel(R.drawable.xiaomi_mi11x_pro_1, "Mi 11X Pro", "XIAOMI", "CHINA"));
        items.add(new ItemModel(R.drawable.asus_rog_5_series_01, "Asus ROG Phone 5 Ultimate", "ASUS", "TAIWAN"));
        items.add(new ItemModel(R.drawable.iphone_13_pro_family_hero, "iPhone 13 Pro Max", "APPLE", "UNITED STATES"));
        items.add(new ItemModel(R.drawable.csm_4_zu_3_realme_gt_master_edition_4058e6ceb2, "Realme GT 5G", "Realme", "CHINA"));


        items.add(new ItemModel(R.drawable.sample1, "OPPO F15", "OPPO", "CHINA"));
        items.add(new ItemModel(R.drawable.oneplus_7t_1569568197150, "ONEPLUS 7T", "ONEPLUS", "CHINA"));
        items.add(new ItemModel(R.drawable.xiaomi_poco_x3_pro_1, "POCO X3 PRO", "XIAOMI", "CHINA"));
        items.add(new ItemModel(R.drawable.vivo_v23_pro_r1, "Vivo V23 5G", "VIVO", "CHINA"));
        items.add(new ItemModel(R.drawable.motorola_moto_g71_5g_neptune_green_1, "Motorola Moto G71 5G", "LENOVO", "UNITED STATES"));
        items.add(new ItemModel(R.drawable.samsung_galaxy_s20_fe_5g_13_1280x720, "Samsung Galaxy S20 FE 5G", "SAMSUNG", "SOUTH KOREA"));
        items.add(new ItemModel(R.drawable.xiaomi_mi11x_pro_1, "Mi 11X Pro", "XIAOMI", "CHINA"));
        items.add(new ItemModel(R.drawable.asus_rog_5_series_01, "Asus ROG Phone 5 Ultimate", "ASUS", "TAIWAN"));
        items.add(new ItemModel(R.drawable.iphone_13_pro_family_hero, "iPhone 13 Pro Max", "APPLE", "UNITED STATES"));
        items.add(new ItemModel(R.drawable.csm_4_zu_3_realme_gt_master_edition_4058e6ceb2, "Realme GT 5G", "Realme", "CHINA"));
        return items;
    }
    private String sites(String nama)
    {
        switch (nama)
        {
            case "OPPO F15" : return "https://gadgets.ndtv.com/oppo-f15-price-in-india-91388";
            case "ONEPLUS 7T": return "https://gadgets.ndtv.com/oneplus-7t-price-in-india-91057";
            case "POCO X3 PRO": return "https://gadgets.ndtv.com/poco-x3-pro-price-in-india-100518";
            case "Vivo V23 5G":return "https://gadgets.ndtv.com/vivo-v23-price-in-india-104872";
            case "Motorola Moto G71 5G":return "https://gadgets.ndtv.com/motorola-moto-g71-price-in-india-104545";
            case "Samsung Galaxy S20 FE 5G":return "https://gadgets.ndtv.com/samsung-galaxy-s20-fe-5g-price-in-india-97350";
            case "Mi 11X Pro": return "https://gadgets.ndtv.com/mi-11x-pro-price-in-india-100966";
            case "Asus ROG Phone 5 Ultimate": return "https://gadgets.ndtv.com/asus-rog-phone-5-ultimate-price-in-india-100542";
            case "iPhone 13 Pro Max": return "https://gadgets.ndtv.com/iphone-13-pro-max-price-in-india-104063";
            case "Realme GT 5G":return "https://gadgets.ndtv.com/realme-gt-5g-price-in-india-100486";
            default: return "https://media.makeameme.org/created/well-i-suggest.jpg";


        }

    }
}
