package com.example.quakereport;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {
    private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1462295443000,\"url\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.5.2\",\"limit\":10,\"offset\":1,\"count\":10},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":0.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1460674294040,\"tz\":720,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":2,\"cdi\":3.4,\"mmi\":5.82,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":798,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",at00o1qxho,pt16030050,us20004vvx,gcmt20160130032510,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.1,\"place\":\"94km SSE of Taron, Papua New Guinea\",\"time\":1453777820750,\"updated\":1460156775040,\"tz\":600,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004uks\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004uks&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":4.1,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"20004uks\",\"ids\":\",us20004uks,gcmt20160126031023,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.537,\"rms\":0.74,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 94km SSE of Taron, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.2454,-5.2952,26]},\"id\":\"us20004uks\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.3,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1460156773040,\"tz\":0,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2,\"mmi\":5.28,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",us10004gy9,gcmt20160125042203,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.201,\"rms\":0.92,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.6493,12]},\"id\":\"us10004gy9\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":3.1,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631430230,\"updated\":1460156770040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gqp\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gqp&format=geojson\",\"felt\":1816,\"cdi\":7.2,\"mmi\":6.6,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"us\",\"code\":\"10004gqp\",\"ids\":\",at00o1gd6r,us10004gqp,ak12496371,gcmt20160124103030,\",\"sources\":\",at,us,ak,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-origin,\",\"nst\":null,\"dmin\":0.72,\"rms\":2.11,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.4051,59.6363,129]},\"id\":\"us10004gqp\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":4.6,\"place\":\"215km SW of Tomatlan, Mexico\",\"time\":1453399617650,\"updated\":1459963829040,\"tz\":-420,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004g4l\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004g4l&format=geojson\",\"felt\":11,\"cdi\":2.7,\"mmi\":3.92,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"10004g4l\",\"ids\":\",at00o1bebo,pt16021050,us10004g4l,gcmt20160121180659,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.413,\"rms\":0.98,\"gap\":74,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 215km SW of Tomatlan, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-106.9337,18.8239,10]},\"id\":\"us10004g4l\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.7,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1459304879040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.8,\"mmi\":6.45,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,pt16014050,at00o0xauk,gcmt20160114032534,\",\"sources\":\",us,pt,at,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.281,\"rms\":0.98,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.781,41.9723,46]},\"id\":\"us10004ebx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"12km WNW of Charagua, Bolivia\",\"time\":1452741928270,\"updated\":1459304879040,\"tz\":-240,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebw\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebw&format=geojson\",\"felt\":3,\"cdi\":2.2,\"mmi\":2.21,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"10004ebw\",\"ids\":\",us10004ebw,gcmt20160114032528,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":5.492,\"rms\":1.04,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 12km WNW of Charagua, Bolivia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-63.3288,-19.7597,582.56]},\"id\":\"us10004ebw\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"74km NW of Rumoi, Japan\",\"time\":1452532083920,\"updated\":1459304875040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004djn\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004djn&format=geojson\",\"felt\":8,\"cdi\":3.4,\"mmi\":3.74,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"10004djn\",\"ids\":\",us10004djn,gcmt20160111170803,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.139,\"rms\":0.96,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 74km NW of Rumoi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[141.0867,44.4761,238.81]},\"id\":\"us10004djn\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":8.5,\"place\":\"227km SE of Sarangani, Philippines\",\"time\":1452530285900,\"updated\":1459304874040,\"tz\":480,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004dj5\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dj5&format=geojson\",\"felt\":1,\"cdi\":2.7,\"mmi\":7.5,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":650,\"net\":\"us\",\"code\":\"10004dj5\",\"ids\":\",at00o0srjp,pt16011050,us10004dj5,gcmt20160111163807,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.144,\"rms\":0.72,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 227km SE of Sarangani, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.8621,3.8965,13]},\"id\":\"us10004dj5\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":9,\"place\":\"Pacific-Antarctic Ridge\",\"time\":1451986454620,\"updated\":1459202978040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004bgk\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004bgk&format=geojson\",\"felt\":0,\"cdi\":1,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"10004bgk\",\"ids\":\",us10004bgk,gcmt20160105093415,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":30.75,\"rms\":0.67,\"gap\":71,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - Pacific-Antarctic Ridge\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-136.2603,-54.2906,10]},\"id\":\"us10004bgk\"}],\"bbox\":[-153.4051,-54.2906,10,158.5463,59.6363,582.56]}";
    /*private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1584995054000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-01-21&endtime=2020-03-23&limit=15&minmagnitude=6&orderby=time\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.8.1\",\"limit\":15,\"offset\":1,\"count\":15},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"Central East Pacific Rise\",\"time\":1584916685065,\"updated\":1584975389040,\"tz\":-420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70008e91\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70008e91&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":572,\"net\":\"us\",\"code\":\"70008e91\",\"ids\":\",us70008e91,pt20082002,at00q7m9jj,\",\"sources\":\",us,pt,at,\",\"types\":\",geoserve,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":23.015999999999998,\"rms\":0.97999999999999998,\"gap\":78,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - Central East Pacific Rise\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-105.05459999999999,-4.6851000000000003,10]},\"id\":\"us70008e91\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2000000000000002,\"place\":\"246km S of Kangin, Indonesia\",\"time\":1584553538838,\"updated\":1584808095002,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60008hzl\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60008hzl&format=geojson\",\"felt\":154,\"cdi\":5.7999999999999998,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":681,\"net\":\"us\",\"code\":\"60008hzl\",\"ids\":\",us60008hzl,pt20078002,at00q7ehc4,\",\"sources\":\",us,pt,at,\",\"types\":\",dyfi,geoserve,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":2.7469999999999999,\"rms\":1.0600000000000001,\"gap\":24,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 246km S of Kangin, Indonesia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[115.131,-11.059100000000001,17.59]},\"id\":\"us60008hzl\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"97km NNW of Sola, Vanuatu\",\"time\":1584501226088,\"updated\":1584587842772,\"tz\":660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60008hkg\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60008hkg&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":3.911,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":572,\"net\":\"us\",\"code\":\"60008hkg\",\"ids\":\",us60008hkg,pt20078000,at00q7dcyz,\",\"sources\":\",us,pt,at,\",\"types\":\",geoserve,ground-failure,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":2.2999999999999998,\"rms\":1.03,\"gap\":23,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 97km NNW of Sola, Vanuatu\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[167.06800000000001,-13.1372,178.81999999999999]},\"id\":\"us60008hkg\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"172km E of Hihifo, Tonga\",\"time\":1584461182225,\"updated\":1584547727136,\"tz\":-660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60008h76\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60008h76&format=geojson\",\"felt\":9,\"cdi\":3.6000000000000001,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":557,\"net\":\"us\",\"code\":\"60008h76\",\"ids\":\",pt20077001,us60008h76,\",\"sources\":\",pt,us,\",\"types\":\",dyfi,geoserve,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":3.7189999999999999,\"rms\":0.68000000000000005,\"gap\":60,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 172km E of Hihifo, Tonga\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-172.13659999999999,-15.995100000000001,10]},\"id\":\"us60008h76\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"298km NE of Raoul Island, New Zealand\",\"time\":1584180077383,\"updated\":1584266633243,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60008fl8\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60008fl8&format=geojson\",\"felt\":3,\"cdi\":2.3999999999999999,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":611,\"net\":\"us\",\"code\":\"60008fl8\",\"ids\":\",us60008fl8,pt20074000,at00q76h61,\",\"sources\":\",us,pt,at,\",\"types\":\",associate,dyfi,geoserve,impact-link,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":2.6869999999999998,\"rms\":1.03,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 298km NE of Raoul Island, New Zealand\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-175.68469999999999,-27.419599999999999,10]},\"id\":\"us60008fl8\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"24km SE of Saray, Turkey\",\"time\":1582473631630,\"updated\":1583166625229,\"tz\":210,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70007v9g\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70007v9g&format=geojson\",\"felt\":17,\"cdi\":7.0999999999999996,\"mmi\":6.6779999999999999,\"alert\":\"yellow\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":662,\"net\":\"us\",\"code\":\"70007v9g\",\"ids\":\",us70007v9g,at00q65wgz,pt20054001,\",\"sources\":\",us,at,pt,\",\"types\":\",dyfi,geoserve,ground-failure,impact-text,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.048,\"rms\":0.90000000000000002,\"gap\":18,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 24km SE of Saray, Turkey\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[44.372900000000001,38.493299999999998,10]},\"id\":\"us70007v9g\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7,\"place\":\"94km ENE of Kuril'sk, Russia\",\"time\":1581590024514,\"updated\":1584450784040,\"tz\":660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70007pa9\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70007pa9&format=geojson\",\"felt\":33,\"cdi\":4.7999999999999998,\"mmi\":6.234,\"alert\":\"yellow\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":770,\"net\":\"us\",\"code\":\"70007pa9\",\"ids\":\",us70007pa9,at00q5myo9,at00q5mcj0,pt20044002,\",\"sources\":\",us,at,at,pt,\",\"types\":\",associate,dyfi,finite-fault,geoserve,ground-failure,impact-link,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":4.5010000000000003,\"rms\":0.83999999999999997,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.0 - 94km ENE of Kuril'sk, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[148.9579,45.614699999999999,144]},\"id\":\"us70007pa9\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"126km S of Kokopo, Papua New Guinea\",\"time\":1581228269967,\"updated\":1584516108895,\"tz\":600,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70007lwy\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70007lwy&format=geojson\",\"felt\":6,\"cdi\":4.2999999999999998,\"mmi\":5.5170000000000003,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":575,\"net\":\"us\",\"code\":\"70007lwy\",\"ids\":\",us70007lwy,pt20040000,at00q5f7jn,\",\"sources\":\",us,pt,at,\",\"types\":\",dyfi,geoserve,ground-failure,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.292,\"rms\":0.98999999999999999,\"gap\":46,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 126km S of Kokopo, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[152.15219999999999,-5.4924999999999997,34]},\"id\":\"us70007lwy\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"113km SSE of Bristol Island, South Sandwich Islands\",\"time\":1581172378625,\"updated\":1583616099648,\"tz\":-120,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70007lik\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70007lik&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":3.4900000000000002,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"70007lik\",\"ids\":\",us70007lik,pt20039002,\",\"sources\":\",us,pt,\",\"types\":\",geoserve,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":8.2010000000000005,\"rms\":0.94999999999999996,\"gap\":85,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 113km SSE of Bristol Island, South Sandwich Islands\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-25.552600000000002,-59.921199999999999,16]},\"id\":\"us70007lik\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"109km SSE of Pondaguitan, Philippines\",\"time\":1580996405791,\"updated\":1584884995160,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70007jwn\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70007jwn&format=geojson\",\"felt\":6,\"cdi\":6.5999999999999996,\"mmi\":3.9399999999999999,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":558,\"net\":\"us\",\"code\":\"70007jwn\",\"ids\":\",us70007jwn,pt20037000,at00q5a8n0,\",\"sources\":\",us,pt,at,\",\"types\":\",dyfi,geoserve,ground-failure,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.8819999999999999,\"rms\":1.2,\"gap\":44,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 109km SSE of Pondaguitan, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.5692,5.4542999999999999,19]},\"id\":\"us70007jwn\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2000000000000002,\"place\":\"108km NNE of Krajan Pangkah Kulon, Indonesia\",\"time\":1580926357734,\"updated\":1582128478062,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70007j6z\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70007j6z&format=geojson\",\"felt\":22,\"cdi\":3.2999999999999998,\"mmi\":1.488,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":599,\"net\":\"us\",\"code\":\"70007j6z\",\"ids\":\",us70007j6z,at00q58ql1,pt20036001,\",\"sources\":\",us,at,pt,\",\"types\":\",dyfi,geoserve,ground-failure,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":5.4790000000000001,\"rms\":1.04,\"gap\":31,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 108km NNE of Krajan Pangkah Kulon, Indonesia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[113.0778,-6.0816999999999997,592.41999999999996]},\"id\":\"us70007j6z\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"70km W of Kirakira, Solomon Islands\",\"time\":1580305789744,\"updated\":1582097634530,\"tz\":660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60007j2w\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60007j2w&format=geojson\",\"felt\":10,\"cdi\":3.3999999999999999,\"mmi\":4.6180000000000003,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":557,\"net\":\"us\",\"code\":\"60007j2w\",\"ids\":\",us60007j2w,pt20029000,\",\"sources\":\",us,pt,\",\"types\":\",dyfi,geoserve,ground-failure,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.6299999999999999,\"rms\":0.80000000000000004,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 70km W of Kirakira, Solomon Islands\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[161.2756,-10.417999999999999,85]},\"id\":\"us60007j2w\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"55km SE of East End, Cayman Islands\",\"time\":1580248516420,\"updated\":1581310617394,\"tz\":-300,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60007iig\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60007iig&format=geojson\",\"felt\":72,\"cdi\":6,\"mmi\":4.9329999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":616,\"net\":\"us\",\"code\":\"60007iig\",\"ids\":\",at00q4u7k4,pt20028002,us60007iig,\",\"sources\":\",at,pt,us,\",\"types\":\",dyfi,geoserve,ground-failure,impact-link,internal-origin,losspager,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":3.1240000000000001,\"rms\":0.84999999999999998,\"gap\":66,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 55km SE of East End, Cayman Islands\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-80.742000000000004,18.9437,10]},\"id\":\"us60007iig\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.7000000000000002,\"place\":\"123km NNW of Lucea, Jamaica\",\"time\":1580238624918,\"updated\":1583797795821,\"tz\":-300,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60007idc\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60007idc&format=geojson\",\"felt\":1514,\"cdi\":9,\"mmi\":6,\"alert\":\"yellow\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1812,\"net\":\"us\",\"code\":\"60007idc\",\"ids\":\",pt20028001,us60007idc,at00q4ttp1,\",\"sources\":\",pt,us,at,\",\"types\":\",dyfi,finite-fault,general-text,geoserve,ground-failure,impact-link,impact-text,losspager,losspager-admin,moment-tensor,origin,phase-data,poster,shakemap,\",\"nst\":null,\"dmin\":1.657,\"rms\":0.75,\"gap\":26,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.7 - 123km NNW of Lucea, Jamaica\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-78.756,19.4193,14.859999999999999]},\"id\":\"us60007idc\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"102km WNW of Kirakira, Solomon Islands\",\"time\":1580101321704,\"updated\":1582007311111,\"tz\":660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60007gyx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60007gyx&format=geojson\",\"felt\":22,\"cdi\":5.7999999999999998,\"mmi\":5.6520000000000001,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":623,\"net\":\"us\",\"code\":\"60007gyx\",\"ids\":\",us60007gyx,pt20027001,at00q4r1zh,\",\"sources\":\",us,pt,at,\",\"types\":\",dyfi,geoserve,ground-failure,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":8.0120000000000005,\"rms\":0.97999999999999998,\"gap\":18,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 102km WNW of Kirakira, Solomon Islands\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[161.06059999999999,-10.0929,21]},\"id\":\"us60007gyx\"}],\"bbox\":[-175.6847,-59.9212,10,167.068,45.6147,592.42]}";
    *///Bloqueia a criacao de objetos QueryUtils
    private QueryUtils(){}

    //Retorna um ArrayList de objetos do Json
    public static ArrayList<Earthquakes> extractEarthquakes() {
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
                arrayList.add(new Earthquakes(responseJson.getDouble("mag"),responseJson.getString("place"),responseJson.getLong("time")));
            }
        } catch (JSONException e){
            //Lanca a excecao de erro
            Log.e("QueryUtils","Falha ao converter os resultados do Json",e);
        }
        //Retorna o ArrayList pronto
        return arrayList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Method to convert Json to ArrayList<Earthquakes>";
    }
}
