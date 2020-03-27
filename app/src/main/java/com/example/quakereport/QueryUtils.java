package com.example.quakereport;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {

    private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1585021368000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-01-21&endtime=2020-03-23&limit=30&orderby=time\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.8.1\",\"limit\":30,\"offset\":1,\"count\":30},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":2.6299999999999999,\"place\":\"10km S of Guanica, Puerto Rico\",\"time\":1584921345680,\"updated\":1584950300822,\"tz\":-240,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/pr2020082022\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=pr2020082022&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":106,\"net\":\"pr\",\"code\":\"2020082022\",\"ids\":\",us70008e9r,pr2020082022,\",\"sources\":\",us,pr,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":22,\"dmin\":0.088300000000000003,\"rms\":0.14000000000000001,\"gap\":219,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 2.6 - 10km S of Guanica, Puerto Rico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-66.952500000000001,17.925799999999999,12]},\"id\":\"pr2020082022\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.6000000000000001,\"place\":\"65km NNW of Nikiski, Alaska\",\"time\":1584920867882,\"updated\":1584921153515,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ak0203s01lr6\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak0203s01lr6&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":39,\"net\":\"ak\",\"code\":\"0203s01lr6\",\"ids\":\",ak0203s01lr6,\",\"sources\":\",ak,\",\"types\":\",geoserve,origin,\",\"nst\":null,\"dmin\":null,\"rms\":0.58999999999999997,\"gap\":null,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.6 - 65km NNW of Nikiski, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-151.88820000000001,61.204099999999997,86.799999999999997]},\"id\":\"ak0203s01lr6\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.94999999999999996,\"place\":\"15km ENE of Ridgecrest, CA\",\"time\":1584920744420,\"updated\":1584970129217,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci39111807\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci39111807&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":14,\"net\":\"ci\",\"code\":\"39111807\",\"ids\":\",ci39111807,\",\"sources\":\",ci,\",\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":14,\"dmin\":0.15540000000000001,\"rms\":0.17999999999999999,\"gap\":150,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.0 - 15km ENE of Ridgecrest, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-117.52549999999999,35.671500000000002,8.4000000000000004]},\"id\":\"ci39111807\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.8700000000000001,\"place\":\"7km SE of La Parguera, Puerto Rico\",\"time\":1584920412630,\"updated\":1584949367624,\"tz\":-240,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/pr2020082021\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=pr2020082021&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":127,\"net\":\"pr\",\"code\":\"2020082021\",\"ids\":\",pr2020082021,\",\"sources\":\",pr,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":6,\"dmin\":0.12870000000000001,\"rms\":0.029999999999999999,\"gap\":236,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 2.9 - 7km SE of La Parguera, Puerto Rico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-66.996799999999993,17.922799999999999,8]},\"id\":\"pr2020082021\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.5,\"place\":\"5km ENE of Indian Hills, Nevada\",\"time\":1584920230311,\"updated\":1584986328904,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nn00720061\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nn00720061&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":4,\"net\":\"nn\",\"code\":\"00720061\",\"ids\":\",nn00720061,\",\"sources\":\",nn,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":6,\"dmin\":0.051999999999999998,\"rms\":0.20569999999999999,\"gap\":140.30000000000001,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.5 - 5km ENE of Indian Hills, Nevada\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.7276,39.1066,10.199999999999999]},\"id\":\"nn00720061\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.9500000000000002,\"place\":\"4km SE of Maricao, Puerto Rico\",\"time\":1584920086060,\"updated\":1584927888040,\"tz\":-240,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/pr2020082020\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=pr2020082020&format=geojson\",\"felt\":2,\"cdi\":3.1000000000000001,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":135,\"net\":\"pr\",\"code\":\"2020082020\",\"ids\":\",us70008e9k,pr2020082020,\",\"sources\":\",us,pr,\",\"types\":\",dyfi,geoserve,origin,phase-data,\",\"nst\":22,\"dmin\":0.14399999999999999,\"rms\":0.13,\"gap\":66,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 3.0 - 4km SE of Maricao, Puerto Rico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-66.945800000000006,18.141999999999999,21]},\"id\":\"pr2020082020\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.2999999999999998,\"place\":\"78km NNW of Talkeetna, Alaska\",\"time\":1584920061898,\"updated\":1584922242040,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ak0203rzyrem\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak0203rzyrem&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":81,\"net\":\"ak\",\"code\":\"0203rzyrem\",\"ids\":\",ak0203rzyrem,us70008e9l,\",\"sources\":\",ak,us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":null,\"rms\":0.52000000000000002,\"gap\":null,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 2.3 - 78km NNW of Talkeetna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-150.87909999999999,62.915999999999997,102.3]},\"id\":\"ak0203rzyrem\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.22,\"place\":\"9km WNW of Cobb, CA\",\"time\":1584919859240,\"updated\":1584922622964,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nc73357525\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nc73357525&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":23,\"net\":\"nc\",\"code\":\"73357525\",\"ids\":\",nc73357525,\",\"sources\":\",nc,\",\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":25,\"dmin\":0.01005,\"rms\":0.040000000000000001,\"gap\":62,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 1.2 - 9km WNW of Cobb, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-122.82267,38.837665600000001,2.5299999999999998]},\"id\":\"nc73357525\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.8,\"place\":\"43km SW of Valdez, Alaska\",\"time\":1584919736199,\"updated\":1584919927748,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ak0203rzxjsj\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak0203rzxjsj&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":50,\"net\":\"ak\",\"code\":\"0203rzxjsj\",\"ids\":\",ak0203rzxjsj,\",\"sources\":\",ak,\",\"types\":\",geoserve,origin,\",\"nst\":null,\"dmin\":null,\"rms\":0.66000000000000003,\"gap\":null,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.8 - 43km SW of Valdez, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-146.85929999999999,60.835099999999997,11.9]},\"id\":\"ak0203rzxjsj\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.5,\"place\":\"5km N of Johnson Lane, Nevada\",\"time\":1584919701071,\"updated\":1584986137481,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nn00720057\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nn00720057&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":4,\"net\":\"nn\",\"code\":\"00720057\",\"ids\":\",nn00720057,\",\"sources\":\",nn,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":7,\"dmin\":0.059999999999999998,\"rms\":0.1132,\"gap\":147.25,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.5 - 5km N of Johnson Lane, Nevada\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.7247,39.098500000000001,9.0999999999999996]},\"id\":\"nn00720057\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.1000000000000001,\"place\":\"35km S of Redoubt Volcano, Alaska\",\"time\":1584919177647,\"updated\":1584919826980,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ak0203rzvkxz\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak0203rzvkxz&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":68,\"net\":\"ak\",\"code\":\"0203rzvkxz\",\"ids\":\",ak0203rzvkxz,\",\"sources\":\",ak,\",\"types\":\",geoserve,origin,\",\"nst\":null,\"dmin\":null,\"rms\":0.29999999999999999,\"gap\":null,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 2.1 - 35km S of Redoubt Volcano, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-152.8321,60.170999999999999,109.8]},\"id\":\"ak0203rzvkxz\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.97999999999999998,\"place\":\"13km NE of Julian, CA\",\"time\":1584918254630,\"updated\":1584918477599,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci39111799\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci39111799&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":15,\"net\":\"ci\",\"code\":\"39111799\",\"ids\":\",ci39111799,\",\"sources\":\",ci,\",\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":21,\"dmin\":0.1148,\"rms\":0.25,\"gap\":54,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.0 - 13km NE of Julian, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-116.4838333,33.145000000000003,8.7699999999999996]},\"id\":\"ci39111799\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.72999999999999998,\"place\":\"41km NW of West Yellowstone, Montana\",\"time\":1584918254190,\"updated\":1584975345340,\"tz\":-420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/mb80386089\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=mb80386089&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":8,\"net\":\"mb\",\"code\":\"80386089\",\"ids\":\",mb80386089,\",\"sources\":\",mb,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":9,\"dmin\":0.087999999999999995,\"rms\":0.17000000000000001,\"gap\":246,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.7 - 41km NW of West Yellowstone, Montana\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-111.5196667,44.890833299999997,16.059999999999999]},\"id\":\"mb80386089\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.6200000000000001,\"place\":\"0km SSW of Ridgely, Tennessee\",\"time\":1584918101020,\"updated\":1584973542240,\"tz\":-360,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nm60284582\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nm60284582&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":40,\"net\":\"nm\",\"code\":\"60284582\",\"ids\":\",nm60284582,\",\"sources\":\",nm,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":18,\"dmin\":0.015779999999999999,\"rms\":0.070000000000000007,\"gap\":57,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 1.6 - 0km SSW of Ridgely, Tennessee\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-89.491500000000002,36.255333299999997,6.7999999999999998]},\"id\":\"nm60284582\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.04,\"place\":\"6km NE of Magna, Utah\",\"time\":1584917957080,\"updated\":1584930978610,\"tz\":-420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/uu60369027\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=uu60369027&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":17,\"net\":\"uu\",\"code\":\"60369027\",\"ids\":\",uu60369027,\",\"sources\":\",uu,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":17,\"dmin\":0.014590000000000001,\"rms\":0.12,\"gap\":59,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 1.0 - 6km NE of Magna, Utah\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-112.05916670000001,40.755333299999997,7.96]},\"id\":\"uu60369027\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.63,\"place\":\"15km NNE of Dillon, Montana\",\"time\":1584916698280,\"updated\":1584981980520,\"tz\":-420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/mb80386154\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=mb80386154&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":6,\"net\":\"mb\",\"code\":\"80386154\",\"ids\":\",mb80386154,\",\"sources\":\",mb,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":6,\"dmin\":0.02,\"rms\":0.059999999999999998,\"gap\":128,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.6 - 15km NNE of Dillon, Montana\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-112.58150000000001,45.345666700000002,6.6500000000000004]},\"id\":\"mb80386154\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"Central East Pacific Rise\",\"time\":1584916685065,\"updated\":1585003230300,\"tz\":-420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70008e91\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70008e91&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":572,\"net\":\"us\",\"code\":\"70008e91\",\"ids\":\",us70008e91,pt20082002,at00q7m9jj,\",\"sources\":\",us,pt,at,\",\"types\":\",geoserve,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":23.015999999999998,\"rms\":0.97999999999999998,\"gap\":78,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - Central East Pacific Rise\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-105.05459999999999,-4.6851000000000003,10]},\"id\":\"us70008e91\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.34999999999999998,\"place\":\"8km WNW of Cobb, CA\",\"time\":1584916456930,\"updated\":1584919143079,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nc73357500\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nc73357500&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":2,\"net\":\"nc\",\"code\":\"73357500\",\"ids\":\",nc73357500,\",\"sources\":\",nc,\",\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":9,\"dmin\":0.01281,\"rms\":0.01,\"gap\":61,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 0.4 - 8km WNW of Cobb, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-122.8116684,38.836166400000003,1.9199999999999999]},\"id\":\"nc73357500\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.0999999999999996,\"place\":\"204km WSW of Hihifo, Tonga\",\"time\":1584916455877,\"updated\":1584917655040,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70008e90\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70008e90&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":400,\"net\":\"us\",\"code\":\"70008e90\",\"ids\":\",us70008e90,\",\"sources\":\",us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":3.6629999999999998,\"rms\":0.82999999999999996,\"gap\":59,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 5.1 - 204km WSW of Hihifo, Tonga\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-175.4033,-16.884499999999999,328.42000000000002]},\"id\":\"us70008e90\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.92000000000000004,\"place\":\"22km N of Ridgecrest, CA\",\"time\":1584915856210,\"updated\":1584916074870,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci39111783\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci39111783&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":13,\"net\":\"ci\",\"code\":\"39111783\",\"ids\":\",ci39111783,\",\"sources\":\",ci,\",\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":13,\"dmin\":0.03671,\"rms\":0.17999999999999999,\"gap\":74,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.9 - 22km N of Ridgecrest, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-117.64266670000001,35.816666699999999,4.6200000000000001]},\"id\":\"ci39111783\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.20000000000000001,\"place\":\"5km ENE of Indian Hills, Nevada\",\"time\":1584915854163,\"updated\":1584986135856,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nn00720056\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nn00720056&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":1,\"net\":\"nn\",\"code\":\"00720056\",\"ids\":\",nn00720056,\",\"sources\":\",nn,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":5,\"dmin\":0.050999999999999997,\"rms\":0.0848,\"gap\":139.37,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.2 - 5km ENE of Indian Hills, Nevada\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.7304,39.107599999999998,7.7999999999999998]},\"id\":\"nn00720056\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.1100000000000001,\"place\":\"2km WNW of East Hill-Meridian, Washington\",\"time\":1584915512950,\"updated\":1585001233350,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/uw61585507\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=uw61585507&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":19,\"net\":\"uw\",\"code\":\"61585507\",\"ids\":\",uw61585507,\",\"sources\":\",uw,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":15,\"dmin\":null,\"rms\":0.14000000000000001,\"gap\":57,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.1 - 2km WNW of East Hill-Meridian, Washington\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-122.208333333333,47.421500000000002,45.700000000000003]},\"id\":\"uw61585507\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.69999999999999996,\"place\":\"5km NE of Indian Hills, Nevada\",\"time\":1584915357619,\"updated\":1584985759819,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nn00720054\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nn00720054&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":8,\"net\":\"nn\",\"code\":\"00720054\",\"ids\":\",nn00720054,\",\"sources\":\",nn,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":7,\"dmin\":0.029000000000000001,\"rms\":0.12820000000000001,\"gap\":132.53,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.7 - 5km NE of Indian Hills, Nevada\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.7328,39.116100000000003,8.4000000000000004]},\"id\":\"nn00720054\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.98999999999999999,\"place\":\"37km W of Lakeview, Oregon\",\"time\":1584915061480,\"updated\":1585001911080,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/uw61585502\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=uw61585502&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":15,\"net\":\"uw\",\"code\":\"61585502\",\"ids\":\",uw61585502,\",\"sources\":\",uw,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":3,\"dmin\":null,\"rms\":0.040000000000000001,\"gap\":133,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 1.0 - 37km W of Lakeview, Oregon\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-120.80133333333301,42.228000000000002,-1.1799999999999999]},\"id\":\"uw61585502\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.7000000000000002,\"place\":\"86km WNW of Larsen Bay, Alaska\",\"time\":1584914908460,\"updated\":1584915663040,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ak0203rz7rql\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak0203rz7rql&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"automatic\",\"tsunami\":0,\"sig\":112,\"net\":\"ak\",\"code\":\"0203rz7rql\",\"ids\":\",ak0203rz7rql,us70008e8i,\",\"sources\":\",ak,us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":null,\"rms\":0.62,\"gap\":null,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 2.7 - 86km WNW of Larsen Bay, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-155.3715,57.794600000000003,81.400000000000006]},\"id\":\"ak0203rz7rql\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0,\"place\":\"4km ENE of Indian Hills, Nevada\",\"time\":1584914699753,\"updated\":1584985571257,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nn00720052\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nn00720052&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":0,\"net\":\"nn\",\"code\":\"00720052\",\"ids\":\",nn00720052,\",\"sources\":\",nn,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":4,\"dmin\":0.058000000000000003,\"rms\":0.074700000000000003,\"gap\":144.24000000000001,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.0 - 4km ENE of Indian Hills, Nevada\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.7337,39.101399999999998,5.9000000000000004]},\"id\":\"nn00720052\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.2999999999999998,\"place\":\"6km NNE of Cape Girardeau, Missouri\",\"time\":1584914346380,\"updated\":1584986473073,\"tz\":-360,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nm60284572\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nm60284572&format=geojson\",\"felt\":27,\"cdi\":3.1000000000000001,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":90,\"net\":\"nm\",\"code\":\"60284572\",\"ids\":\",nm60284572,us70008e83,\",\"sources\":\",nm,us,\",\"types\":\",dyfi,geoserve,origin,phase-data,\",\"nst\":50,\"dmin\":0.13819999999999999,\"rms\":0.22,\"gap\":63,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 2.3 - 6km NNE of Cape Girardeau, Missouri\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-89.504666700000001,37.362000000000002,13.220000000000001]},\"id\":\"nm60284572\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.0800000000000001,\"place\":\"21km N of Friday Harbor, Washington\",\"time\":1584914151510,\"updated\":1584914894060,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/uw61585487\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=uw61585487&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":67,\"net\":\"uw\",\"code\":\"61585487\",\"ids\":\",uw61585487,\",\"sources\":\",uw,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":17,\"dmin\":null,\"rms\":0.20000000000000001,\"gap\":122,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 2.1 - 21km N of Friday Harbor, Washington\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-122.97150000000001,48.724333333333298,57.909999999999997]},\"id\":\"uw61585487\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.42999999999999999,\"place\":\"5km SSW of Mammoth Lakes, CA\",\"time\":1584914016040,\"updated\":1584998883136,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nc73357495\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nc73357495&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":3,\"net\":\"nc\",\"code\":\"73357495\",\"ids\":\",nc73357495,\",\"sources\":\",nc,\",\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":11,\"dmin\":0.018290000000000001,\"rms\":0.050000000000000003,\"gap\":225,\"magType\":\"md\",\"type\":\"earthquake\",\"title\":\"M 0.4 - 5km SSW of Mammoth Lakes, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.0111667,37.596333299999998,0.65000000000000002]},\"id\":\"nc73357495\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":0.69999999999999996,\"place\":\"5km SSE of Carson City, Nevada\",\"time\":1584913622990,\"updated\":1584982969561,\"tz\":-480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nn00720038\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nn00720038&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":8,\"net\":\"nn\",\"code\":\"00720038\",\"ids\":\",nn00720038,\",\"sources\":\",nn,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":9,\"dmin\":0.027,\"rms\":0.14940000000000001,\"gap\":131.34999999999999,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 0.7 - 5km SSE of Carson City, Nevada\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-119.7346,39.117600000000003,8.0999999999999996]},\"id\":\"nn00720038\"}],\"bbox\":[-175.4033,-16.8845,-1.18,-66.9458,62.916,328.42]}}";
    //Bloqueia a criacao de objetos QueryUtils
    private QueryUtils(){}

    /* Le o arquivo response.json
    public static String loadJSONResponse(Context context){
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("response.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }*/

    //Retorna um ArrayList de objetos do Json
    /*public static ArrayList<Earthquakes> extractEarthquakes() {
        //Cria um array List para adicionar os objetos "Earthquakes"
        ArrayList<Earthquakes> arrayList = new ArrayList<>();
        //Cria o block Try Catch para pegar excecoes do tipo "JSONException"
        try {
            //Faz a conversao dos elementos do array
            JSONObject responseJson = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray featuresArray = responseJson.getJSONArray("features");
            for(int i = 0; i<featuresArray.length();i++){
                responseJson = featuresArray.getJSONObject(i);
                responseJson = responseJson.getJSONObject("properties");
                arrayList.add(new Earthquakes(responseJson.getDouble("mag"),responseJson.getString("place"),responseJson.getLong("time"),responseJson.getString("url")));
            }
        } catch (JSONException e){
            //Lanca a excecao de erro
            Log.e("QueryUtils","Falha ao converter os resultados do Json",e);
        }
        //Retorna o ArrayList pronto
        return arrayList;
    }*/
    static ArrayList<Earthquakes> extractEarthquakes(String stringUrl) {
        // Cria a conexao com a Url
        URL url = createUrl(stringUrl);

        // Faz a requisicao HTTP e recebe o arquivo JSON
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(QueryUtils.class.getSimpleName(),"Erro ao fazer a requisicao HTTP");
        }
        // Cria um array List para adicionar os objetos "Earthquakes"
        ArrayList<Earthquakes> arrayList = extractDataFromJson(jsonResponse);
        // Retorna o arrayList pronto
        return arrayList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        // Tenta criar a URL
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(QueryUtils.class.getSimpleName(),"Erro criando a Url");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // Verifica se a URL e nula
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        // Tenta criar a conexao para receber os arquivos JSON
        try {
            // Prepara a conexao
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            // Faz a conexao com a Url
            httpURLConnection.connect();
            // Verifica se a conexao foi bem sucedida (codigo 200)
            if (httpURLConnection.getResponseCode() == 200){
                // Pega os dados de entrada no httpUrlConection
                inputStream = httpURLConnection.getInputStream();
                // Le os dados de entrada e coloca na string jsonResponse
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(QueryUtils.class.getSimpleName(),"Erro codigo: "+httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(QueryUtils.class.getSimpleName(),"Erro ao abrir conexao.");
        } finally {
            // Apos as conexoes fecha as conexoes abertas
            // independente de erros
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream){
        // Cria uma nova SringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        // Verifica se a entrada de dados e nula
        if (inputStream != null){
            // Le os dados da entrada
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // Le linha por linha do bufferedReader
            try {
                String line = bufferedReader.readLine();
                // Enquanto tiver linhas adiciona a linha o fm da string line
                while (line != null){
                    stringBuilder.append(line);
                    // Le uma nova linha
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(QueryUtils.class.getSimpleName(),"Nao ha linhas para serem lidas. Erro: "+e);
            }
        }
        return stringBuilder.toString();
    }

    private static ArrayList<Earthquakes> extractDataFromJson(String jsonResponse) {
        // Verifica se o JSON esta vazio
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        //Cria um array List para adicionar os objetos "Earthquakes"
        ArrayList<Earthquakes> arrayList = new ArrayList<>();
        //Cria o block Try Catch para pegar excecoes do tipo "JSONException"
        try {
            //Faz a conversao dos elementos do array
            JSONObject responseJson = new JSONObject(jsonResponse);
            JSONArray featuresArray = responseJson.getJSONArray("features");
            if (featuresArray.length() > 0){
                for(int i = 0; i<featuresArray.length();i++){
                    responseJson = featuresArray.getJSONObject(i);
                    responseJson = responseJson.getJSONObject("properties");
                    arrayList.add(new Earthquakes(responseJson.getDouble("mag"),responseJson.getString("place"),responseJson.getLong("time"),responseJson.getString("url")));
                }
            }
        } catch (JSONException e){
            //Lanca a excecao de erro
            Log.e(QueryUtils.class.getSimpleName(),"Falha ao converter os resultados do Json",e);
        }
        //Retorna o ArrayList pronto
        return arrayList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Convert Json to ArrayList<Earthquakes>";
    }
}
