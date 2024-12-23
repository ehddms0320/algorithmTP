package server.data;

import java.util.*;
import server.models.Landmark;
import server.models.Airport;
import server.constants.TravelTags;

public class TravelData {
    private static Map<String, List<Landmark>> landmarks;
    private static Map<String, List<Airport>> airports;
    private static boolean initialized = false;

    private static void initialize() {
        if (!initialized) {
            initializeLandmarks();
            initializeAirports();
            initialized = true;
        }
    }

    public static List<Landmark> getLandmarks(String country) {
        initialize();
        List<Landmark> countryLandmarks = landmarks.get(country);
        return countryLandmarks != null ? new ArrayList<>(countryLandmarks) : new ArrayList<>();
    }

    public static List<Airport> getAirports(String country) {
        initialize();
        return airports.get(country);
    }

    private static void initializeLandmarks() {
        landmarks = new HashMap<>();
    
        // South Korea Landmarks
        List<Landmark> koreaLandmarks = new ArrayList<>();
        koreaLandmarks.addAll(Arrays.asList(
            new Landmark(1L, "경복궁", "South Korea",
                "조선 왕조의 법궁이자 한국의 대표적인 문화유산",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                37.5796, 126.9770),
            new Landmark(2L, "남산서울타워", "South Korea",
                "서울의 상징적인 랜드마크이자 전망대",
                Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
                37.5511, 126.9882),
            new Landmark(3L, "해운대 해수욕장", "South Korea",
                "부산의 상징적인 해변과 마린시티의 야경",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name()),
                35.1586, 129.1603),
            new Landmark(4L, "한강공원", "South Korea",
                "서울을 가로지르는 한강변의 여유로운 공원",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.ENTERTAINMENT.name()),
                37.5281, 126.9337),
            new Landmark(5L, "북촌 한옥마을", "South Korea",
                "전통 한옥들이 모여 있는 아름다운 마을",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                37.5826, 126.9830),
            new Landmark(6L, "창덕궁", "South Korea",
                "유네스코 세계문화유산으로 지정된 조선 시대 궁궐",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                37.5794, 126.9910),
            new Landmark(7L, "제주 한라산", "South Korea",
                "제주의 중심에 위치한 한국의 최고봉",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                33.3617, 126.5292),
            new Landmark(8L, "부산 광안리 해수욕장", "South Korea",
                "아름다운 해변과 광안대교의 야경으로 유명한 곳",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
                35.1535, 129.1186),
            new Landmark(9L, "전주 한옥마을", "South Korea",
                "전통 한옥과 풍부한 음식 문화로 유명한 마을",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                35.8150, 127.1505),
            new Landmark(10L, "설악산 국립공원", "South Korea",
                "다양한 생태계와 아름다운 경관을 자랑하는 국립공원",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                38.1194, 128.4656),
            new Landmark(11L, "경주 불국사", "South Korea",
                "유네스코 세계문화유산으로 지정된 신라 시대 사찰",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name()),
                35.7905, 129.3313),
            new Landmark(12L, "남이섬", "South Korea",
                "아름다운 자연과 문화 예술이 공존하는 섬",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
                37.7902, 127.5256),
            new Landmark(13L, "안동 하회마을", "South Korea",
                "전통 가옥과 풍습이 보존된 유네스코 세계문화유산 마을",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                36.5400, 128.5154),
            new Landmark(14L, "수원 화성", "South Korea",
                "조선 시대의 성곽 도시로 유네스코 세계문화유산",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                37.2850, 127.0190),
            new Landmark(15L, "제주 성산일출봉", "South Korea",
                "일출 명소로 유명한 유네스코 세계자연유산",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                33.4588, 126.9415),
            new Landmark(16L, "부산 자갈치 시장", "South Korea",
                "한국 최대의 수산 시장으로 다양한 해산물을 즐길 수 있는 곳",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name(), TravelTags.FOOD.name()),
                35.0975, 129.0370),
            new Landmark(17L, "서울 동대문 디자인 플라자", "South Korea",
                "현대적인 건축물과 다양한 전시가 열리는 문화 복합 공간",
                Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
                37.5665, 127.0090),
            new Landmark(18L, "제주 우도", "South Korea",
                "제주도 동쪽에 위치한 작은 섬으로 아름다운 해변과 자연 경관을 자랑",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                33.5050, 126.9532),
            new Landmark(19L, "강릉 경포대", "South Korea",
                "고려 시대의 누각과 아름다운 경포호수를 자랑하는 명소",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.NATURAL.name(), TravelTags.CULTURAL.name()),
                37.7955, 128.8962),
            new Landmark(20L, "부여 백제문화단지", "South Korea",
                "백제의 역사와 문화를 재현한 복합 관광지",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                36.2764, 126.9299),
            new Landmark(21L, "광주 국립아시아문화전당", "South Korea",
                "아시아 문화를 공유하고 전시하는 현대적 복합 문화 공간",
                Arrays.asList(TravelTags.MODERN.name(), TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name()),
                35.1461, 126.9195),
            new Landmark(22L, "인천 차이나타운", "South Korea",
                "한국 내 유일한 차이나타운으로 다양한 중국 문화 체험",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.FOOD.name(), TravelTags.TRADITIONAL.name()),
                37.4740, 126.6175),
            new Landmark(23L, "제주 만장굴", "South Korea",
                "세계에서 가장 긴 용암 동굴 중 하나로 유네스코 세계자연유산",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
                33.5203, 126.7701),
            new Landmark(24L, "진해 군항제", "South Korea",
                "매년 봄에 열리는 벚꽃 축제로 유명한 축제",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.SEASONAL.name(), TravelTags.ENTERTAINMENT.name()),
                35.1520, 128.6812),
            new Landmark(25L, "부산 감천문화마을", "South Korea",
                "화려한 벽화와 예술 작품으로 꾸며진 독특한 마을",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name(), TravelTags.ARTISTIC.name()),
                35.0974, 129.0102),
            new Landmark(26L, "포항 호미곶", "South Korea",
                "한반도의 가장 동쪽 끝으로 유명한 일출 명소",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                36.0741, 129.5546),
            new Landmark(27L, "여수 오동도", "South Korea",
                "봄철 동백꽃이 아름다운 작은 섬",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                34.7455, 127.7616),
            new Landmark(28L, "대구 서문시장", "South Korea",
                "한국 전통 시장의 모습을 간직한 대규모 시장",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.FOOD.name(), TravelTags.TRADITIONAL.name()),
                35.8690, 128.5836),
            new Landmark(29L, "제주 천지연 폭포", "South Korea",
                "제주도의 대표적인 폭포 중 하나로 아름다운 자연경관",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                33.2505, 126.5628),
            new Landmark(30L, "서울 이태원", "South Korea",
                "다양한 국제적인 문화와 음식을 즐길 수 있는 지역",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.FOOD.name()),
                37.5348, 126.9946),
            new Landmark(31L, "전주 경기전", "South Korea",
                "조선 태조의 어진이 봉안된 유서 깊은 사당",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                35.8150, 127.1506),
            new Landmark(32L, "제주 비자림", "South Korea",
                "제주도의 아름다운 자연림과 산책로",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                33.4886, 126.8087),
            new Landmark(33L, "대전 엑스포과학공원", "South Korea",
                "과학과 기술을 체험할 수 있는 현대적 공원",
                Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
                36.3741, 127.3846),
            new Landmark(34L, "서울 홍대거리", "South Korea",
                "젊음과 예술, 다양한 문화가 공존하는 활기찬 거리",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.ARTISTIC.name()),
                37.5572, 126.9220),
            new Landmark(35L, "광주 무등산", "South Korea",
                "호남 지역의 중심에 위치한 국립공원",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                35.1527, 126.9777),
            new Landmark(36L, "서울 롯데월드타워", "South Korea",
                "세계에서 가장 높은 건물 중 하나로 다양한 볼거리 제공",
                Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
                37.5125, 127.1026),
            new Landmark(37L, "강릉 오죽헌", "South Korea",
                "율곡 이이의 생가로 한국 전통 가옥의 모습을 간직",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
                37.7644, 128.8744),
            new Landmark(38L, "충주 수안보 온천", "South Korea",
                "전통과 현대가 공존하는 한국 대표 온천",
                Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.RELAXATION.name()),
                36.8430, 127.9941),
            new Landmark(39L, "서울 국립중앙박물관", "South Korea",
                "한국의 역사와 문화를 한눈에 볼 수 있는 박물관",
                Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.EDUCATIONAL.name()),
                37.5236, 126.9807),
            new Landmark(40L, "제주 용두암", "South Korea",
                "용의 머리를 닮은 독특한 바위",
                Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
                33.5155, 126.4906)
        ));
        landmarks.put("South Korea", koreaLandmarks);

        // Japan Landmarks
        List<Landmark> japanLandmarks = new ArrayList<>();
        japanLandmarks.addAll(Arrays.asList(
        new Landmark(1L, "도쿄 스카이트리", "Japan",
            "세계에서 가장 높은 전파탑 중 하나이자 도쿄의 새로운 랜드마크",
            Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
            35.7100, 139.8107),
        new Landmark(2L, "후시미이나리 신사", "Japan",
            "수천 개의 붉은 도리이로 유명한 신사",
            Arrays.asList(TravelTags.RELIGIOUS.name(), TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name()),
            34.9671, 135.7727),
        new Landmark(3L, "후지산", "Japan",
            "일본의 상징이자 최고봉",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
            35.3606, 138.7274),
        new Landmark(4L, "오사카 성", "Japan",
            "일본의 대표적인 성 중 하나로 역사적 가치가 큼",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            34.6873, 135.5259),
        new Landmark(5L, "킨카쿠지", "Japan",
            "황금으로 빛나는 절로 유명한 관광지",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.RELIGIOUS.name()),
            35.0394, 135.7292),
        new Landmark(6L, "아라시야마 대나무숲", "Japan",
            "교토에 위치한 대규모 대나무숲으로 유명한 자연 관광지",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
            35.0094, 135.6675),
        new Landmark(7L, "도다이지", "Japan",
            "나라시에 위치한 대불상이 있는 사찰",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name()),
            34.6888, 135.8398),
        new Landmark(8L, "츠키지 시장", "Japan",
            "도쿄의 대표적인 수산 시장",
            Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.FOOD.name(), TravelTags.TRADITIONAL.name()),
            35.6626, 139.7705),
        new Landmark(9L, "도쿄 디즈니랜드", "Japan",
            "세계적으로 유명한 디즈니 테마파크",
            Arrays.asList(TravelTags.ENTERTAINMENT.name(), TravelTags.MODERN.name(), TravelTags.FAMILY.name()),
            35.6340, 139.8804),
        new Landmark(10L, "아키하바라", "Japan",
            "애니메이션과 전자제품으로 유명한 도쿄의 거리",
            Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name()),
            35.6983, 139.7745),
        new Landmark(11L, "삿포로 맥주 박물관", "Japan",
            "삿포로 맥주의 역사를 배우고 맥주를 맛볼 수 있는 장소",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.FOOD.name()),
            43.0687, 141.3535),
        new Landmark(12L, "오키나와 츄라우미 수족관", "Japan",
            "세계 최대급 수족관 중 하나로 해양 생물을 관찰할 수 있는 곳",
            Arrays.asList(TravelTags.ENTERTAINMENT.name(), TravelTags.EDUCATIONAL.name(), TravelTags.FAMILY.name()),
            26.6945, 127.8772),
        new Landmark(13L, "히로시마 평화 기념 공원", "Japan",
            "세계 평화를 기원하며 원폭 피해를 기리는 공원",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.EDUCATIONAL.name()),
            34.3955, 132.4536),
        new Landmark(14L, "닛코 도쇼구", "Japan",
            "도쿠가와 이에야스의 묘소로 유네스코 세계문화유산에 등재",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.RELIGIOUS.name()),
            36.7578, 139.5993),
        new Landmark(15L, "요코하마 차이나타운", "Japan",
            "다양한 중국 음식을 즐길 수 있는 요코하마의 차이나타운",
            Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.FOOD.name(), TravelTags.TRADITIONAL.name()),
            35.4437, 139.6380),
        new Landmark(16L, "도쿄돔", "Japan",
            "일본 프로 야구의 중심지이자 다양한 이벤트가 열리는 돔",
            Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.SPORTS.name()),
            35.7054, 139.7510),
        new Landmark(17L, "나가사키 글로버 가든", "Japan",
            "나가사키에 위치한 19세기 서양식 정원",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.NATURAL.name()),
            32.7356, 129.8687),
        new Landmark(18L, "하코네 온천", "Japan",
            "일본의 대표적인 온천 지역으로 자연과 함께 즐길 수 있는 곳",
            Arrays.asList(TravelTags.RELAXATION.name(), TravelTags.NATURAL.name(), TravelTags.CULTURAL.name()),
            35.1911, 139.0256),
        new Landmark(19L, "미야지마 이쓰쿠시마 신사", "Japan",
            "바다 위에 떠 있는 도리이로 유명한 유네스코 세계문화유산",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name()),
            34.2956, 132.3194),
        new Landmark(20L, "시라카와고", "Japan",
            "전통 합장 가옥으로 유명한 산악 지역의 마을",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            36.2564, 136.8991),
            new Landmark(21L, "다카야마 전통 가옥 거리", "Japan",
            "에도 시대의 전통 가옥이 보존된 거리",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            36.1407, 137.2510),
        new Landmark(22L, "가나자와 켄로쿠엔", "Japan",
            "일본 3대 정원 중 하나로 아름다운 경관을 자랑",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
            36.5613, 136.6560),
        new Landmark(23L, "오키나와 슈리성", "Japan",
            "류큐 왕국 시대의 성으로 유네스코 세계문화유산",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            26.2174, 127.7195),
        new Landmark(24L, "고베 하버랜드", "Japan",
            "고베 항구의 대표적인 쇼핑과 관광지",
            Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
            34.6814, 135.1859),
        new Landmark(25L, "나카스 카와바타 아케이드", "Japan",
            "후쿠오카의 전통과 현대가 공존하는 상점가",
            Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.FOOD.name(), TravelTags.ENTERTAINMENT.name()),
            33.5891, 130.4023),
        new Landmark(26L, "아소산", "Japan",
            "세계 최대의 칼데라를 자랑하는 활화산",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
            32.8842, 131.1064),
        new Landmark(27L, "벳푸 온천", "Japan",
            "다양한 온천과 온천 문화로 유명한 지역",
            Arrays.asList(TravelTags.RELAXATION.name(), TravelTags.NATURAL.name(), TravelTags.CULTURAL.name()),
            33.2794, 131.5007),
        new Landmark(28L, "요코하마 랜드마크 타워", "Japan",
            "일본에서 가장 높은 빌딩 중 하나로 멋진 전망 제공",
            Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
            35.4540, 139.6317),
        new Landmark(29L, "오이타 다케다 성터", "Japan",
            "구름 위의 성으로 불리는 아름다운 성터",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
            35.2634, 134.8298),
        new Landmark(30L, "도쿄 오모테산도", "Japan",
            "도쿄의 고급 쇼핑 거리와 현대 건축물",
            Arrays.asList(TravelTags.MODERN.name(), TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name()),
            35.6672, 139.7085),
        new Landmark(31L, "기온 거리", "Japan",
            "교토의 전통적인 게이샤 문화와 찻집이 보존된 거리",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            35.0037, 135.7781),
        new Landmark(32L, "센다이 조 성터", "Japan",
            "센다이의 역사적인 성터로 멋진 경관을 자랑",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
            38.2596, 140.8491),
        new Landmark(33L, "오키나와 국제거리", "Japan",
            "오키나와의 문화와 음식을 즐길 수 있는 거리",
            Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.FOOD.name(), TravelTags.ENTERTAINMENT.name()),
            26.2142, 127.6792),
        new Landmark(34L, "히메지 성", "Japan",
            "일본에서 가장 잘 보존된 성으로 유네스코 세계문화유산",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            34.8394, 134.6939),
        new Landmark(35L, "츠쿠바 산", "Japan",
            "등산과 자연 경관으로 유명한 산",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
            36.2258, 140.1034),
        new Landmark(36L, "나고야 성", "Japan",
            "에도 시대의 웅장한 성",
            Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
            35.1850, 136.8991),
        new Landmark(37L, "시부야 스크램블 교차로", "Japan",
            "세계적으로 유명한 교차로로 도쿄의 번화함을 상징",
            Arrays.asList(TravelTags.MODERN.name(), TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name()),
            35.6595, 139.7005),
        new Landmark(38L, "홋카이도 비에이 언덕", "Japan",
            "홋카이도의 아름다운 언덕 풍경",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
            43.5887, 142.4676),
        new Landmark(39L, "구로베 협곡 철도", "Japan",
            "멋진 풍경을 자랑하는 협곡 철도",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.ENTERTAINMENT.name()),
            36.7555, 137.5096),
        new Landmark(40L, "노토 반도", "Japan",
            "일본 해안을 따라 펼쳐진 아름다운 자연과 문화 지역",
            Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
            37.4564, 137.3416)
    ));
    landmarks.put("Japan", japanLandmarks);
    
    // Vietnam Landmarks
    List<Landmark> vietnamLandmarks = new ArrayList<>();
    vietnamLandmarks.addAll(Arrays.asList(
    new Landmark(1L, "하롱베이", "Vietnam",
        "세계자연유산으로 지정된 석회암 카르스트 지형",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
        20.9101, 107.1839),
    new Landmark(2L, "호이안 고대도시", "Vietnam",
        "유네스코 세계문화유산으로 지정된 전통 거리",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
        15.8801, 108.3265),
    new Landmark(3L, "통일궁", "Vietnam",
        "베트남 전쟁 시기의 대통령궁",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.MODERN.name()),
        10.7772, 106.6958),
    new Landmark(4L, "미선 유적지", "Vietnam",
        "고대 참파 왕국의 종교적 중심지",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name()),
        15.7662, 108.2156),
    new Landmark(5L, "사파 계곡", "Vietnam",
        "아름다운 논 계단으로 유명한 베트남의 자연 풍경",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        22.3400, 103.8444),
    new Landmark(6L, "노이바이 국제공항", "Vietnam",
        "하노이의 주요 국제공항",
        Arrays.asList(TravelTags.MODERN.name(), TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name()),
        21.2187, 105.8047),
    new Landmark(7L, "하노이 문묘", "Vietnam",
        "유교와 학문의 상징으로 유명한 고대 사원",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.RELIGIOUS.name()),
        21.0285, 105.8355),
    new Landmark(8L, "다낭 드래곤 브릿지", "Vietnam",
        "다낭의 현대적 상징인 드래곤 모양의 다리",
        Arrays.asList(TravelTags.MODERN.name(), TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name()),
        16.0628, 108.2232),
    new Landmark(9L, "다랏 사랑의 계곡", "Vietnam",
        "낭만적인 풍경으로 유명한 계곡과 자연 공원",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
        11.9404, 108.4598),
    new Landmark(10L, "꾸찌 터널", "Vietnam",
        "베트남 전쟁 중 게릴라 전술에 사용된 터널 시스템",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.EDUCATIONAL.name()),
        11.1433, 106.4648),
    new Landmark(11L, "호치민 시티 오페라 하우스", "Vietnam",
        "프랑스 식민지 시대의 건축 양식을 보여주는 오페라 하우스",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.ENTERTAINMENT.name()),
        10.7769, 106.7004),
    new Landmark(12L, "빈펄 랜드", "Vietnam",
        "베트남의 유명한 놀이공원과 리조트",
        Arrays.asList(TravelTags.ENTERTAINMENT.name(), TravelTags.MODERN.name(), TravelTags.FAMILY.name()),
        12.2213, 109.2026),
    new Landmark(13L, "무이네 사막", "Vietnam",
        "베트남에서 보기 드문 사막 지형과 모래언덕",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        10.9252, 108.2583),
    new Landmark(14L, "쩐꾸옥 사원", "Vietnam",
        "하노이의 오래된 불교 사원 중 하나",
        Arrays.asList(TravelTags.RELIGIOUS.name(), TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name()),
        21.0458, 105.8360),
    new Landmark(15L, "후에 왕궁", "Vietnam",
        "응우옌 왕조의 중심지로 유네스코 세계문화유산",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
        16.4637, 107.5909),
    new Landmark(16L, "판시판 산", "Vietnam",
        "인도차이나 반도에서 가장 높은 산",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        22.3043, 103.7783),
    new Landmark(17L, "하노이 호안끼엠 호수", "Vietnam",
        "도심에 위치한 아름다운 호수와 전설적인 거북이 탑",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
        21.0285, 105.8526),
    new Landmark(18L, "다낭 마블 마운틴", "Vietnam",
        "대리석으로 이루어진 다섯 개의 산으로 유명한 명소",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name()),
        16.0143, 108.2548),
    new Landmark(19L, "호치민 독립궁", "Vietnam",
        "베트남 전쟁 종식을 기념하는 역사적 건물",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.MODERN.name()),
        10.7769, 106.6955),
    new Landmark(20L, "푸꾸옥 섬", "Vietnam",
        "아름다운 해변과 리조트로 유명한 베트남의 휴양 섬",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        10.2899, 103.9840),
    new Landmark(21L, "깟바 섬", "Vietnam",
        "하롱베이 근처의 아름다운 섬으로 자연 경관과 해변이 유명",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        20.7278, 107.0488),
    new Landmark(22L, "바 나 힐", "Vietnam",
        "다낭 근처에 위치한 산악 리조트와 골든 브릿지가 있는 명소",
        Arrays.asList(TravelTags.ENTERTAINMENT.name(), TravelTags.CULTURAL.name(), TravelTags.NATURAL.name()),
        15.9951, 107.9894),
    new Landmark(23L, "짱안 경관 단지", "Vietnam",
        "유네스코 세계유산으로 지정된 아름다운 석회암 지형",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
        20.2487, 105.9120),
    new Landmark(24L, "까마우 곶", "Vietnam",
        "베트남 최남단에 위치한 자연 보호구역",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        8.6749, 104.6997),
    new Landmark(25L, "빈퐁 야생동물 보호구역", "Vietnam",
        "멸종 위기 동물을 보호하기 위한 자연 생태계",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.EDUCATIONAL.name()),
        11.6484, 106.8819),
    new Landmark(26L, "라오까이 계단식 논", "Vietnam",
        "사파 근처에 위치한 아름다운 계단식 논밭",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.CULTURAL.name(), TravelTags.OUTDOOR.name()),
        22.3383, 103.8401),
    new Landmark(27L, "남카국립공원", "Vietnam",
        "다양한 생물종이 서식하는 생태학적 공원",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.EDUCATIONAL.name()),
        11.9545, 107.6878),
    new Landmark(28L, "호치민 미술관", "Vietnam",
        "현대 및 전통 예술을 전시하는 미술관",
        Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.ARTISTIC.name(), TravelTags.EDUCATIONAL.name()),
        10.7725, 106.6961),
    new Landmark(29L, "땀꼭", "Vietnam",
        "베트남의 '육지 하롱베이'라 불리는 경치 좋은 곳",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        20.2255, 105.9063),
    new Landmark(30L, "포가낭 타워", "Vietnam",
        "참족의 유산으로 알려진 역사적인 타워",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name()),
        12.2388, 109.1924),
    new Landmark(31L, "푸마쯔오 해변", "Vietnam",
        "깨끗한 해변과 휴양지로 유명한 관광 명소",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        12.1000, 109.2170),
    new Landmark(32L, "베트남 민속촌", "Vietnam",
        "베트남 전통 문화와 생활 방식을 체험할 수 있는 장소",
        Arrays.asList(TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name(), TravelTags.EDUCATIONAL.name()),
        21.0079, 105.7952),
    new Landmark(33L, "까오다이 사원", "Vietnam",
        "다양한 종교가 혼합된 독특한 건축 양식을 가진 사원",
        Arrays.asList(TravelTags.RELIGIOUS.name(), TravelTags.CULTURAL.name(), TravelTags.ARTISTIC.name()),
        11.3560, 106.1864),
    new Landmark(34L, "박마 국립공원", "Vietnam",
        "베트남 중부에 위치한 열대 우림과 산악 지대",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        16.2160, 107.8543),
    new Landmark(35L, "녹후에 동굴", "Vietnam",
        "세계에서 가장 큰 동굴 중 하나로 유명한 곳",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        17.5476, 106.2879),
    new Landmark(36L, "판디엣 화이트샌드", "Vietnam",
        "무이네 근처에 위치한 백사장과 사막 지형",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        10.9471, 108.1992),
    new Landmark(37L, "호치민 중앙 우체국", "Vietnam",
        "프랑스 식민지 시대 건축 양식을 가진 랜드마크",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.MODERN.name()),
        10.7798, 106.6992),
    new Landmark(38L, "사이공 강", "Vietnam",
        "호치민시의 중심을 흐르는 강으로 보트 투어가 유명",
        Arrays.asList(TravelTags.NATURAL.name(), TravelTags.OUTDOOR.name(), TravelTags.CULTURAL.name()),
        10.7821, 106.7027),
    new Landmark(39L, "다이노이 왕궁", "Vietnam",
        "후에에 위치한 응우옌 왕조의 옛 수도",
        Arrays.asList(TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name(), TravelTags.TRADITIONAL.name()),
        16.4637, 107.5810),
    new Landmark(40L, "사이공 노트르담 대성당", "Vietnam",
        "프랑스 식민지 시대에 지어진 고딕 양식의 성당",
        Arrays.asList(TravelTags.RELIGIOUS.name(), TravelTags.HISTORICAL.name(), TravelTags.CULTURAL.name()),
        10.7794, 106.6992)
    ));
    landmarks.put("Vietnam", vietnamLandmarks);

    }
    private static void initializeAirports() {
        airports = new HashMap<>();
        
        // Korea Airports
        airports.put("South Korea", Arrays.asList(
            new Airport("ICN", "인천국제공항", "South Korea", 37.4602, 126.4407),
            new Airport("GMP", "김포국제공항", "South Korea", 37.5586, 126.7944)
        ));

        // Japan Airports
        airports.put("Japan", Arrays.asList(
            new Airport("HND", "하네다 국제공항", "Japan", 35.5494, 139.7798),
            new Airport("NRT", "나리타 국제공항", "Japan", 35.7720, 140.3929)
        ));

        // Vietnam Airports
        airports.put("Vietnam", Arrays.asList(
            new Airport("HAN", "노이바이 국제공항", "Vietnam", 21.2187, 105.8047),
            new Airport("SGN", "탄손녓 국제공항", "Vietnam", 10.8184, 106.6520)
        ));
    }
}
