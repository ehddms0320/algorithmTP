package server.data;

import server.models.Airport;
import server.models.Landmark;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TravelData {
    private static final Map<String, Airport> airports = new HashMap<>();
    private static final Map<String, List<Landmark>> landmarks = new HashMap<>();

    static {
        initializeAirports();
        initializeLandmarks();
    }

    private static void initializeAirports() {
        // 각 나라의 주요 공항 데이터
        airports.put("South Korea", new Airport(
            "Incheon International Airport",
            "South Korea",
            37.4602,
            126.4407,
            "ICN"
        ));

        airports.put("Japan", new Airport(
            "Narita International Airport",
            "Japan",
            35.7720,
            140.3929,
            "NRT"
        ));

        airports.put("Vietnam", new Airport(
            "Noi Bai International Airport",
            "Vietnam",
            21.2187,
            105.8047,
            "HAN"
        ));
    }

    private static void initializeLandmarks() {
        // 한국 랜드마크
        List<Landmark> koreaLandmarks = new ArrayList<>();
        koreaLandmarks.add(new Landmark("Gyeongbokgung Palace", "South Korea", 
            new String[]{"history", "culture", "palace"}, 37.5796, 126.9770));
        koreaLandmarks.add(new Landmark("Namsan Seoul Tower", "South Korea", 
            new String[]{"view", "modern", "landmark"}, 37.5511, 126.9882));
        koreaLandmarks.add(new Landmark("Bukchon Hanok Village", "South Korea", 
            new String[]{"culture", "traditional", "architecture"}, 37.5830, 126.9860));
        koreaLandmarks.add(new Landmark("Jeju Island", "South Korea", 
            new String[]{"nature", "island", "beach"}, 33.4996, 126.5312));
        koreaLandmarks.add(new Landmark("Seoraksan National Park", "South Korea", 
            new String[]{"nature", "mountain", "hiking"}, 38.1194, 128.4659));
        koreaLandmarks.add(new Landmark("Changdeokgung Palace", "South Korea", 
            new String[]{"history", "culture", "palace"}, 37.5826, 126.9910));
        koreaLandmarks.add(new Landmark("DMZ", "South Korea", 
            new String[]{"history", "military", "landmark"}, 37.9400, 126.6745));
        koreaLandmarks.add(new Landmark("Myeongdong Shopping Street", "South Korea", 
            new String[]{"shopping", "urban", "culture"}, 37.5637, 126.9827));
        koreaLandmarks.add(new Landmark("Dongdaemun Design Plaza", "South Korea", 
            new String[]{"modern", "architecture", "design"}, 37.5665, 127.0090));
        koreaLandmarks.add(new Landmark("Busan Haeundae Beach", "South Korea", 
            new String[]{"nature", "beach", "urban"}, 35.1587, 129.1603));
        koreaLandmarks.add(new Landmark("Gamcheon Culture Village", "South Korea", 
            new String[]{"culture", "art", "landmark"}, 35.0973, 129.0104));
        // TODO: 나머지 29개의 한국 랜드마크 추가

        // 일본 랜드마크
        List<Landmark> japanLandmarks = new ArrayList<>();
        japanLandmarks.add(new Landmark("Tokyo Tower", "Japan", 
            new String[]{"modern", "view", "landmark"}, 35.6586, 139.7454));
        japanLandmarks.add(new Landmark("Senso-ji Temple", "Japan", 
            new String[]{"temple", "culture", "history"}, 35.7147, 139.7966));
        japanLandmarks.add(new Landmark("Mount Fuji", "Japan", 
            new String[]{"nature", "mountain", "view"}, 35.3606, 138.7274));
        japanLandmarks.add(new Landmark("Osaka Castle", "Japan", 
            new String[]{"history", "culture", "landmark"}, 34.6873, 135.5259));
        japanLandmarks.add(new Landmark("Arashiyama Bamboo Grove", "Japan", 
            new String[]{"nature", "forest", "view"}, 35.0095, 135.6678));
        japanLandmarks.add(new Landmark("Hiroshima Peace Memorial Park", "Japan", 
            new String[]{"history", "peace", "landmark"}, 34.3955, 132.4537));
        japanLandmarks.add(new Landmark("Nara Park", "Japan", 
            new String[]{"nature", "wildlife", "park"}, 34.6851, 135.8502));
        japanLandmarks.add(new Landmark("Gion District", "Japan", 
            new String[]{"culture", "traditional", "urban"}, 35.0037, 135.7722));
        japanLandmarks.add(new Landmark("Okinawa Churaumi Aquarium", "Japan", 
            new String[]{"nature", "marine", "family"}, 26.6941, 127.8774));
        japanLandmarks.add(new Landmark("Nikko Toshogu Shrine", "Japan", 
            new String[]{"culture", "temple", "history"}, 36.7522, 139.5991));
        // TODO: 나머지 30개의 일본 랜드마크 추가

        // 베트남 랜드마크
        List<Landmark> vietnamLandmarks = new ArrayList<>();
        vietnamLandmarks.add(new Landmark("Ha Long Bay", "Vietnam", 
            new String[]{"nature", "water", "view"}, 20.9101, 107.1839));
        vietnamLandmarks.add(new Landmark("Hoan Kiem Lake", "Vietnam", 
            new String[]{"nature", "culture", "urban"}, 21.0285, 105.8524));
        vietnamLandmarks.add(new Landmark("Imperial City", "Vietnam", 
            new String[]{"history", "culture", "architecture"}, 16.4598, 107.5776));
        vietnamLandmarks.add(new Landmark("Phong Nha-Ke Bang National Park", "Vietnam", 
            new String[]{"nature", "caves", "adventure"}, 17.6052, 106.2872));
        vietnamLandmarks.add(new Landmark("My Son Sanctuary", "Vietnam", 
            new String[]{"culture", "temple", "history"}, 15.7767, 108.1112));
        vietnamLandmarks.add(new Landmark("Cu Chi Tunnels", "Vietnam", 
            new String[]{"history", "military", "landmark"}, 10.8919, 106.4656));
        vietnamLandmarks.add(new Landmark("Golden Bridge", "Vietnam", 
            new String[]{"architecture", "view", "landmark"}, 15.9953, 107.9975));
        vietnamLandmarks.add(new Landmark("Hoi An Ancient Town", "Vietnam", 
            new String[]{"culture", "history", "urban"}, 15.8801, 108.3380));
        vietnamLandmarks.add(new Landmark("Ba Na Hills", "Vietnam", 
            new String[]{"nature", "view", "landmark"}, 15.9955, 107.9927));
        vietnamLandmarks.add(new Landmark("Sa Pa Terraces", "Vietnam", 
            new String[]{"nature", "mountain", "view"}, 22.3358, 103.8470));
        // TODO: 나머지 30개의 베트남 랜드마크 추가

        landmarks.put("South Korea", koreaLandmarks);
        landmarks.put("Japan", japanLandmarks);
        landmarks.put("Vietnam", vietnamLandmarks);
    }

    public static Airport getAirport(String country) {
        return airports.get(country);
    }

    public static List<Landmark> getLandmarks(String country) {
        return landmarks.get(country);
    }

    public static List<String> getCountries() {
        return new ArrayList<>(airports.keySet());
    }
}
