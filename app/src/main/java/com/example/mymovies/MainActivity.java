//1. Создаем 3 активности Main, Detail, Favourite
//1.1 Создаем класс NetworkUtils и сохраняем туда ключ доступа к API, базовый URL для JSON
//1.2 Сохраняем в NetworkUtils переменные нужных параметров для загрузки API. И значения этих
//параметров
//1.3 Создаем целочисленные public переменные для способов сортировки фильмов(0,1)
//1.4 Создаем метод который формирует URL-запрос. С помощью URI builder и принимает в качестве
//параметра метод сортировки и номер страницы
//1.5 Создаем методы AsyncTask для загрузки JSON объекта
//1.6 Добавляем в манифест доступ в интернет
//2. Создаем класс Movie с конструктором, геттерами и сеттерами для каждого поля
//3. Создаем класс JSONUtils в нем будем преобразовывать jsonObjectFromNetwork в массив
// фильмов Movie
//4. Создаем макет маленького постера к фильму movie_item.xml с корневым элементом ImageView
//4.1 Добавляем элементы в макет activity_main.xml
//5. Создаем адаптер MovieAdapter
//5.1 В адаптере создаем класс MovieViewHolder и наследуем от RecyclerView.ViewHolder
//5.2 Из movie_item.xml передаем imageView во ViewHolder и присваиваем значение по ID
//5.3 Наследуем MovieAdapter от класса ViewHolder c помощью RecyclerView.Adapter<>
//5.4 Создаем в адаптере массив Movie c конструктором геттерами и сеттерами в него будем
// передавать массив фильмов
//5.5 Добавляем в адаптер метод добавления фильмов в массив, чтобы при пролистывании не заменять
// целиком массив в адаптере, а только добавлять новые элементы. В качестве параметра метод принимает массив
//5.6 При измениях массива уведомляем об этом адаптер
//6. Добавляем в Gradle зависимости для библиотеки Picasso, которая упрощает работу с картинками
// а также кэширует их для работы без интернета
//6.1 В метод onCreateViewHolder передаем с помощью LayoutInflater передаем макет movie_item.xml
//6.2 В метод getItemCount передаем размер массива в адаптере
//6.3 В методе onBindViewHolder загружаем картинку во ViewHolder с помощью Picasso
//7. В MainActivity устанавливаем отображение сеткой и адаптер для RecyclerView
//7.1 В MainActivity добавляем OnCheckedChangeListener на Switch
//7.2 Устанавливаем начальное положение setChecked(true). Слушатель событий будет выполняться только
//после изменения положения. Тоесть добавляем еще и setChecked(false) после объявления метода слушателя
//7.3 Создаем метод setMethodOfSort(boolean isChecked) и запускаем его из слушателя передавая
// положение Switch. Из объекта JSON получаем массив фильмов и передаем через сеттер в адаптер
// по способу сортировки(в зависимости от положения switch).
//7.4 Добавляем метод setMethodOfSort(boolean isChecked) в onClickSetTopRated и onClickSetPopularity
// и меняем положение Switch
//8. В themes.xml меняем основные цвета на черные
//9. В метод setMethodOfSort(boolean isChecked) добавляем изменение цвета для TextView
//10. Добавляем во ViewHolder interface подгрузки новых данных при достижении конца массива
// в адаптере или за несколько позиций до этого
//10.1 В MainActivity устанавливаем у адаптера интерфейс с помощью сеттера и выводим тост END OF LIST
//11. Добавляем dependencies для AAC(Android Architecture Components);
//12. Делаем аннонтации для входных значений базы данных к классе Movie
//12.1 Создаем интерфейс для доступа к базе данных. Метод возвращающий массив всех заметок в LiveData
// Метод возвращающий заметку по id. Метод удаления всех заметок из базы. Метод вставки заметки.
// Метод удаления заметки. Все методы помечаем аннотациями, а интерфейс помечечаем Dao
//13. Создаем абстрактный класс базы данных с помощью паттерна Singleton(чтобы не было возможности создать
// несколько объектов базы данных) и с добавлением блока синхронизации(чтобы не было возможности
// создавать 2 базы одновременно из нескольких потоков). Класс базы данных наследуется от
//Room.database
//13.1 В базе данных добавляем абстрактный метод который возвращает класс MovieDao
//13.2 Создаем класс MainViewModel наследуемый от AndroidViewModel который работает отдельным
// потоком и позволяет сохранять например UI при перевороте экрана. Аналог onSaveInstanceState,
// только для больших объемов данных. Получаем с помощью Singleton объект базы данных
// и объект LiveData для фильмов. В конструкторе присваиваем для базы данных значение с помощью
// MovieDatabase.getInstance и с помощью интерфейса database.MovieDao.getAllMovies() загружаем
// фильмы из базы данных в объект LiveData
//13.3 Создаем AsyncTask методы для загрузки и удаления фильмов в базу данных
//14. В MainActivity создаем объект ViewModel и присваиваем значение в onCreate()
//15. Создаем отдельный метод для загрузки данных downloadData и запускаем его из setMethodOfSort
// проверяем если данные были загружены в массив из JSON, то очищаем базу данных с помощью
// MainViewModel и загружаем в цикле полученный массив JSON в базу данных через MainViewModel
//16. Создаем observer для LiveData
//16.1 Получаем объект LiveData в onCreate и присваиваем ему текущее значение LiveData из ViewModel
//16.2 Устанавливаеам observer для LiveData. Передаем массив из LiveData через observer в адаптер
//Тоесть теперь при каждом изменении LiveData, observer устанавливает массив из LiveData в адаптер
// через сеттер
//17. Наполняем макет activity_detail.xml и присваиваем значение в DetailActivity
//18. В адаптере создаем интерфейс слушателя щелчков на постер, сеттер и объект интерфейса
///18.1 Добавляем во ViewHolder анонимный класс itemView.setOnClickListener и если объект интерфейса
// не null, то вызываем у объекта интерфейс и передаем ему позицию адаптера
//18.2 В MainActivity устанавливаем у адаптера интерфейс с помощью сеттера, узнаем id нажатого
//фильма и передаем id через интент в DetailActivity
//19. Создаем объект MainViewModel и присваиваем ему значение. Получаем объект фильма по id
// из базы данных через ViewModel и присваиваем значение элементам макета activity_detail
//20. Добавляем картинку звездочки в activity_detail.xml и метод onCLick
//20.1 Создаем класс новой таблицы FavouriteMovie наследуемый от Movie и делаем аннотация с
// названием новой таблицы
//20.2 Добавляем в базу данных новую таблицу, изменяем номер версии и добавляем метод
//fallbackToDestructiveMigration для пересоздания базы данных с новыми таблицами
//20.3 В DAO добавляем методы загрузки и удаления фильмов в новую таблицу
//20.4 В MainViewModel создаем новый объект LiveData и добавляем туда массив FavouriteMovie
//20.5 Создаем AsyncTask методы для загрузки и удаления фильмов в новую таблицу
//21. В DetailActivity создаем метод setFavouriteMovie() в котором присваиваем значение для объекта
// FavouriteMovie из таблицы favourite_movies по id. Проверяем, если значение присвоилось то меняем
// цвет звездочки на желтый, а если FavouriteMovie остался null то на серый. Вызываем этот метод из onCreate()
//21.1 В onClickChangeFavourite проверяем присвоено ли значение для объекта FavouriteMovie и если нет
// то значит в таблице favourite_movies этого фильма нет. Добавляем туда объект Movie из интента
// преобразовывая в FavouriteMovie(для это нужно сделать преобразующий конструктор в классе FavouriteMovie)
// и пометить @Ignore чтобы не мешал базе данных. И изменяем цвет звездочки с помощью setFavouriteMovie()
//22. Добавляем меню. Создаем main_menu.xml в !! в папке res/menu. И добавляем пункты меню через <item
//title и id< item>/
//23. Во всех активностях где нужно меню переопределяем методы onCreateOptionsMenu и onOptionsItemSelected
//23.1 В onCreateOptionsMenu передаем menu_item.xml через Menu Inflater.inflate
//23.2 В onOptionsItemSelected получаем id нажатого элемента меню и с помошью switch/case отправляем в
// нужную активность
//24. В FavouriteActivity добавляем RecyclerView в который будет загружаться база избранных фильмов
//25. Дальше все делаем как в MainActivity(устанавливаем адаптер для recyclerView, отображение сеткой
// и так далее) Чтобы передать в адаптер <List<FavouriteMovies>> загружаем фильмы сначала в массив
//  <List<Movie>> movies = new ArrayList<>(), movies.addAll(). Затем передаем массив в адаптер через
// сеттер
//26. При нажатии на избранный фильм запускаем DetailActivity с этим фильмом. С помощью onPosterClickListener
// и интента
//27. Создаем метод который формирует URL-запрос по базовому URL для трейлеров. И метод для отзывов.
// Делаем с помощью URI builder, который принимает в качестве параметра id фильма
//28. Создаем классы Review и Trailer с конструкторами, геттерами и сеттерами
//28.1 В классе JSONUtils преобразовываем JSON объекты из NetworkUtils в массивы Review и Trailer
//29. Создаем макеты trailer_item и review_item
//30. Создаем адаптер ReviewAdapter
//31. Создаем TrailerAdapter с интерфейсом для слушателя щелчков, который принимает в качестве
// параметра URL трейлера. Объект интерфейса и сеттер для установки интерфейса. Устанавливаем
// слушатель щелчков у itemView и передаем ему URL из ячейки массива адаптера равный номеру позиции адаптера
//32. В DetailActivity устанавливаем адаптеры для обеих RecyclerView
//32.1 DetailActivity устанавливаем слушатель событий на адаптер с трейлерами и выбрасывам Тост с URL
// видео. Получается что при нажатии на трейлер
//32.2 Получаем JSON объекты с трейлерами и отзывами. Преобразуем их в массивы и передаем в адаптеры
//33. Создаем конструктор Movie без uniqueId для того чтобы была возможность самим создавать объекты
// Movie. Помечаем конструтор @Ignore
//34. В NetworkUtils создаем public класс для подгрузки данных в базу при пролистывании списка фильмов
// JSONLoader extends AsyncTaskLoader<JSONObject> (он не крашится при повороте экрана или при
// плохом соединении). И переопределяем методы автоматически. И метод onStartLoading.forceLoad вручную
//34.1 Передаем в конструктор Bundle(который будет содержать URL для загрузки JSON объекта)
//34.2 В методе loadInBackground получаем URL из переданного Bundle. И возвращаем JSON объект
// полученный из URL
//35. Для всего класса MainActivity реализуем интерфейс LoaderCallBack<JSON> и переопределяем методы
//35.1 Создаем переменные для LOADER_ID и для LoaderManager, присваиваем значение для менеджера в
// onCreate, getInstance(this). Значение для LOADER_ID указываем любое
//35.2 В методе onCreateLoader создаем объект JSONLoader и передаем параметры this и bundle
// Этот метод после выполнения вернет JSONObject в onLoadFinished
//35.3 В методе onLoadFinished загружаем полученный JSONObject в базу данных
//35.4 После загрузки удаляем загрузчик по Id loader.manager.destroyLoader(id)
//35.5 В методе downloadData получаем URL и вставляем его в Bundle. Вставлять данные в базу уже не
//нужно в этом методе
//35.6 Запускаем загрузчик через loadManager.restartLoader
//36. Исправляем приложение чтобы  фильмы добавлялись в базу данных и адаптер одновременно
//36.1 Удаляем установку сеттера адаптера при изменении базы данных в LiveData
//36.2 Добавляем фильмы в адаптер в методе onLoadFinished movieAdapter.addMoviesToAdapterArrayList(movieFromJSON);
//37. Добавляем переменную которая хранит начальный номер страницы. И будет увеличиваться при
// окончании загрузки это страницы
//37.1 При изменении метода сортировки начальная страницы снова становится 1. А при завершении
// метода onLoadFinished увеличивается на 1
//37.2 Добавляем в вызов интерфейса OnReachEnd условие чтобы он не вызывался если фильмов в адаптере
// меньше 20. Тоесть фильмы не будут подгружаться если не загружена первая страница
//38. Создаем boolean isLoading = false. Который будет изменяться на true при начале подгрузки
// фильмов. Для этого в AsyncTaskLoader добавляем интерфейс OnStartLoadingListener, объект интерфейса
// и сеттер на него
//38.1 В методе onStartLoading проверяем если OnStartLoadingListener != null, то запускаем интерфейс
// OnStartLoadingListener.onStartLoading()
//38.2 В методе onCreateLoader устанавливаем интерфейс и присваиваем isLoading = true;
//38.3 При завершении загрузки onLoadFinished присваиваем isLoading = false
//38.4 В методе onReachEnd если isLoading = false вызываем downloadData()
//38.5 В метод onLoadFinished добавляем проверку if(page == 1). Чтобы база данных очищалась только
// если приложение только запущено и страница равна 1. А если страница не равна 1 значит в базу
//не происходит загрузки из интернета. И базу очищать нельзя
//38.6 Добавляем в адаптер public метод clear() и очищаем массив адаптера после очистки базы
//38.7 При изменении базы данных добавляем в observer условие if(page == 1), то загружаем в адаптер
// массив фильмов из observer
//39. Запрещаем внутреннюю прокрутку у RecyclerView nestedScrollingEnabled = "false", т.к он на ходится
// внутри ScrollView
//40. Добавляем ProgressBar. Делаем его GONE в макете, VISIBLE в начале загрузки и по окончании
//INVISIBLE
//41. Добавляем метод который рассчитывает ширину экрана и возвращает нужное количество колонок
//для LayoutManager
//42. Создаем макет movie_info.xml с общими элементами макета для activity_detail.xml и
// landscape activity_detail.xml
//42.1 Перематываем ScrollView в самый верх списка
//43. Добавить локализации
//44. Установить иконки приложения через ImageAsset
//45. Сгенерировать release версию приложения через GenerateSignedApk-AndroidAppBundle


package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Реализовать интерфейс загрузчика
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private RecyclerView recyclerViewPosters;

    private MovieAdapter movieAdapter;

    private SwitchCompat switchSort;

    private TextView textViewPopularity;
    private TextView textViewTopRated;

    private MainViewModel mainViewModel;

    //Рандомный ID загрузчика
    private static final int LOADER_ID = 101;
    private LoaderManager loaderManager;

    private static int page = 1;
    private static boolean isLoading = false;
    private static int methodOfSort;

    private ProgressBar progressBarLoading;

    private static String lang;

    // При создании Menu передать макет main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Получить id нажатого элемента Menu и запустить нужную активность
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuItemToMain:
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
                break;
            case R.id.menuItemToFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Вернуть кол-во колонок c фильмами в зависимости от ширины экрана
    private int getColumnCount()    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //Получить параметры экрана телефона и сохранить в DisplayMetrics
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        /* Перевести ширину из обычных пикселей в аппаратно-независимые пиксели делением на
        плотность */
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        //Вернуть кол-во колонок, которое может вместить экран, иначе "2"
        return Math.max(width / 185, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Получить язык устройства
        lang = Locale.getDefault().getLanguage();

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        loaderManager = LoaderManager.getInstance(this);

        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);

        progressBarLoading = findViewById(R.id.progressBarLoading);

        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);

        switchSort = findViewById(R.id.switchSort);
        // Установить слушатель на Switch и его начальное положение
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // При переключении Switch загружаем фильмы начиная с первой страницы
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                page = 1;
                setMethodOfSort(isChecked);
            }
        });
        // Чтобы слушатель сработал при создании активности переключаем Switch
        switchSort.setChecked(false);

        //Установить интерфейс у адаптера и получить позицию нажатого постера
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClickPosition(int position) {
                //Получить id фильма по позиции нажатого постера
                int id = movieAdapter.getMovieAdapterArrayList().get(position).getId();
                // Запустить DetailActivity и передать id фильма
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        // Установить интерфейс для ранней подгрузки данных
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                // Если загрузка еще не начата загрузить данные
                if(!isLoading)   {
                    downloadData(methodOfSort, page);
                }
            }
        });
        //Создать observer для LiveData
        LiveData<List<Movie>> movieFromLiveData = mainViewModel.getLiveDataMovies();
        movieFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            // При изменении базы данных, установить новый массив фильмов в адаптере
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovieAdapterArrayList(movies);
            }
        });
    }

    // Установить цвет текста и метод сортировки по позиции Switch
    private void setMethodOfSort(boolean isChecked) {
        if (isChecked) {
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getColor(R.color.teal_200));
            textViewPopularity.setTextColor(getColor(R.color.white));
        } else {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getColor(R.color.teal_200));
            textViewTopRated.setTextColor(getColor(R.color.white));
        }
        // Начать загрузку фильмов из интернета по методу сортировки и номеру страницы
        downloadData(methodOfSort, page);
    }

    /* При нажатиях на TextView с методами сортировки вызывать те же методы как и при переключении
    Switch */
    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }
    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    // Загрузить фильмы из интернета по методу сортировки и номеру страницы
    private void downloadData(int methodOfSort, int page) {
        URL url = NetworkUtils.buildURL(methodOfSort, page, lang);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        // Запустить загрузчик данных и передать ему URL через Bundle
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    // Передать в загрузчик bundle с URL
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this, args);
        // Установить на загрузчик слушатель начала загрузки
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                //Сделать видимым ProgressBar
                progressBarLoading.setVisibility(View.VISIBLE);
                /* Запретить вызов downloadData() при пролистывании списка фильмов до окончания
                текущей загрузки */
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    // Получить из загрузчика массив фильмов и вставить в таблицу "movies" и в адаптер
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movieFromJSON = JSONUtils.getMoviesFromJSON(data);
        /* Если приложение недавно открыто и данные загрузились из интернета очистить таблицу "movies"
        и адаптер */
        if (movieFromJSON != null && !movieFromJSON.isEmpty()) {
            if(page == 1)   {
                mainViewModel.deleteAllMovies();
                movieAdapter.clearAdapter();
            }
            // Вставить новый массив в таблицу "movies"
            for (Movie movie : movieFromJSON) {
                mainViewModel.insertMovie(movie);
            }
            // Вставить новый массив в адаптер
            movieAdapter.addMoviesToAdapterArrayList(movieFromJSON);
            // Увеличить номер страницы
            page++;
        }

        /* Разрешить вызов downloadData() при пролистывании списка фильмов после завершения текущей
        для загрузки */
        isLoading = false;
        //Сделать невидимым ProgressBar
        progressBarLoading.setVisibility(View.INVISIBLE);
        // Уничтожить загрузчик
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}