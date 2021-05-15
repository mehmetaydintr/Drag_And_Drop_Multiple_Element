# Drag And Drop Multiple Element

<img src="https://user-images.githubusercontent.com/37263322/118361667-26c2fe80-b595-11eb-8ceb-a39cd3d61745.gif" width="300">


## İçerik

1. [Kullanılan Teknolojiler](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#kullan%C4%B1lan-teknolojiler)
2. [Proje Tanımı](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#proje-tan%C4%B1m%C4%B1)
3. [Kod Tanımı](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#kod-tan%C4%B1m%C4%B1)


## Kullanılan Teknolojiler

  + Android Studio

![Image of Android Studio](https://www.xda-developers.com/files/2017/04/android-studio-logo.png)

  + Java

![Image of Java](https://yazilimamelesi.files.wordpress.com/2013/03/java_logo.jpg)


## Proje Tanımı

Bu proje **Drag & Drop** özelliğini kullanıdğım bir Android Mobil Uygulamasıdır. Drag & Drop hayatımızdaki bir çok uygulamalarda bulunmaktadır. Drag & Drop bir ekran nesnesini (ikon) seçip işaretleyerek, bir başka ekran nesnesinin içine koymak anlamında kullanılan bir GUI deyimi. Yani sürükle bırak tekniğidir. Drag & Drop hem uygulamaya görsel olarak güzellik katarken aynı zamanda kullanıcı deneyimini geliştirmektedir. Kullanıcıların ilgisini çekmektedir.

## Kod Tanımı

+ İlk olarak tasarım ile başlayalım. Basit bir tasarım yapacağız. Üç adet `Lineer Layout`, bir adet `TextView`, bir adet `Button` ve bir adet `ImageView` yerleştirelim.

![1](https://user-images.githubusercontent.com/37263322/118360308-acdc4680-b58f-11eb-8a30-c09ee1dfba22.png)

+ Şimdi Java kodlarına geçebiliriz. İlk olarak gerekli tanımlamalarımızı yapıyoruz. Görsel nesnelerimizi daha kolay tanımlayabilmek için `BUTTON_ETIKET`, `TEXT_ETIKET`, `RESIM_ETIKET` etiketler oluşturuyoruz. Bu projede **Lineer Layout** kullandığımız için `LayoutParams`'a ihtiyacımız olmayacak.

```
private LinearLayout ust_tasarim, sol_tasarim, sag_tasarim;
private Button button;
private TextView textView;
private ImageView imageView;

private static final String TEXT_ETIKET = "YAZI";
private static final String BUTTON_ETIKET = "BUTON";
private static final String RESIM_ETIKET = "RESIM";
```

+ Görsel nesnelerimiz için oluşturduğumuz etiketleri `main` metodu içerisinde ekliyoruz.

```
textView.setTag(TEXT_ETIKET);
button.setTag(BUTTON_ETIKET);
imageView.setTag(RESIM_ETIKET);
```

+ Birden fazla görsel nesne ve layoutumuz olduğu için bu sefer tek tek **listener**lar eklemek yerine direkt `main` sınıfımıza kullanacağımız listener interfacelerini ekliyoruz. 

```
public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener
```

+ Daha sonrasında bu interfaceleri ilgili nesnelerle ilişkilendirmemiz gerekiyor.

```
imageView.setOnLongClickListener(this);
button.setOnLongClickListener(this);
textView.setOnLongClickListener(this);

ust_tasarim.setOnDragListener(this);
sol_tasarim.setOnDragListener(this);
sag_tasarim.setOnDragListener(this);
```

+ Bu interfacelerin kendine özgü metotları bulunmaktadır. Bu metotları eklememiz gerekmektedir.

Drag & Drop işlemimizi tetiklemek için görsel nesnelerimizle ilişkilendirdiğimiz `OnLongClickListener` interface'ine ait `onLongClick` metodunun içerisini kodlayacağız. `ClipData` android işletim sisteminde bir şeyleri (metin, resim, emoji, veya tasarım öğesi vb.) kopyalamak için kullanılıyor. Bizde görsel nesnelerimizi kopyalayıp daha sonra bırakıldığı yere yapıştıracağız. Kısaca aşağıdaki kod bloğu görsel nesnelerimizi kopyalayıp **visibitily** özelliğini invisible yapıyor.

```
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
```

**Lineer Layout**larımıza  Drag & Drop işlemlerini takip edebilmesi için ilişkilendirdiğimiz `OnDragListener` interface'ine ait `onDrag` metodunun içerisini kodlayacağız. `onDrag` metodunda `DragEvent` parametresini kullanarak hangi Drag & Drop action'unun gerçekleştiğini ele alabileceğiz. Bunun için `switch case` yapısı kullanarak bu actionlardan faydalanacağız.

| Action | İşlevi |
|    :---:     |     :---       |
| **DragEvent.ACTION_DRAG_STARTED** | Drag & Drop işlemi başladığı zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_ENTERED** | Görsel nesne belirli bir alana veya layouta girdiği zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_EXITED** | Görsel nesne belirli bir alandan veya layoutan çıktığı zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_LOCATION** | Görsel nesne konumlandırıldığı zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_ENDED** | Drag & Drop işlemi bittiği zaman tetiklenir. |
| **DragEvent.ACTION_DROP** | Görsel nesne bırakıldığı zaman tetiklenir. |

Görsel nesnelerimiz `Lineer Layout`lardan herhangi birisinin içerisine girdiği zaman o layoutumuzun üzerine sarı renkli bir filtre ekliyoruz. Görsel nesnelerimiz `Lineer Layout`lardan herhangi birisinin içerisinden çıktığı zaman, Drag & Drop işlemi bittiği zaman veya görsel nesnelerimizi bıraktığımız zaman o layoutumuzun üzerindeki sarı renkli filtreyi kaldırıyoruz. Görsel nesne bırakıldığı zaman görsel nesnelerimizi yeniden tanımlıyoruz (Görsel nesne herhangi formatta olabileceği için `View` olarak oluşturuyoruz). Layout'umuzun türü herhangi bir formda olabileceği için `ViewGroup` kullanarak mevcut layoutumuzu tanımlıyoruz ve görsel nesnemizi bu layout içerisinden siliyoruz. Daha sonra yeniden bir layout tanımlıyoruz ve içerisine görsel nesnemizi ekleyip **visibility** özelliğini visible yapıyoruz. 

```
@Override
public boolean onDrag(View view, DragEvent dragEvent) {

    switch (dragEvent.getAction()) {
        case DragEvent.ACTION_DRAG_STARTED:
            return true;
        case DragEvent.ACTION_DRAG_ENTERED:
            
            ////Layout üzerine sarı renkli filtre ekleme
            view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
            view.invalidate();
            return true;
        case DragEvent.ACTION_DRAG_EXITED:
            
            //Layout üzerindeki sarı renkli filtreyi kaldırma
            view.getBackground().clearColorFilter();
            view.invalidate();
            return true;
        case DragEvent.ACTION_DRAG_LOCATION:
            return true;
        case DragEvent.ACTION_DRAG_ENDED:
        
            //Layout üzerindeki sarı renkli filtreyi kaldırma
            view.getBackground().clearColorFilter();
            view.invalidate();
            return true;
        case DragEvent.ACTION_DROP:
            
            //Layout üzerindeki sarı renkli filtreyi kaldırma
            view.getBackground().clearColorFilter();
            view.invalidate();

            //Buttonumuzu ve mevcut layoutumuzu tanımlayıp buttounumuzu layoutumuz içerisinden silme
            View gorselNesne = (View) dragEvent.getLocalState();
            ViewGroup eskiTasarimAlani = (ViewGroup) gorselNesne.getParent();
            eskiTasarimAlani.removeView(gorselNesne);

            //Yeni bir layout nesnesi tanımla ve buttonumuzu layoutParams ile ekleme
            LinearLayout hedefTasarimAlani = (LinearLayout) view;
            hedefTasarimAlani.addView(gorselNesne);

            gorselNesne.setVisibility(View.VISIBLE);

            return true;
        default:
            return false;
    }
}
```
