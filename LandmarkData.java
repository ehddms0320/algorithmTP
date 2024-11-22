package al;

import java.util.*;

public class LandmarkData {

    // 각 나라별 랜드마크와 태그 데이터
    private static Map<String, Map<String, List<String>>> landmarkData;

    public static void initializeData() {
        landmarkData = new HashMap<>();

        // 대한민국 랜드마크와 태그
        Map<String, List<String>> koreaLandmarks = new HashMap<>();
        koreaLandmarks.put("경복궁", Arrays.asList("역사", "문화", "전통"));
        koreaLandmarks.put("불국사", Arrays.asList("역사", "문화", "전통"));
        koreaLandmarks.put("설악산", Arrays.asList("자연", "산"));
        koreaLandmarks.put("한라산", Arrays.asList("자연", "산"));
        koreaLandmarks.put("해운대", Arrays.asList("해변", "자연"));
        koreaLandmarks.put("광안리", Arrays.asList("해변", "자연"));
        koreaLandmarks.put("전주한옥마을", Arrays.asList("문화", "전통"));
        koreaLandmarks.put("남산골한옥마을", Arrays.asList("문화", "전통"));
        koreaLandmarks.put("백두산", Arrays.asList("자연", "산"));
        koreaLandmarks.put("경주문화유산", Arrays.asList("문화", "역사"));
        koreaLandmarks.put("국립중앙박물관", Arrays.asList("문화", "미술관"));
        koreaLandmarks.put("한국민속촌", Arrays.asList("문화", "전통"));
        koreaLandmarks.put("서울 N타워", Arrays.asList("전망", "도시"));
        koreaLandmarks.put("동대문디자인플라자", Arrays.asList("건축", "쇼핑"));
        koreaLandmarks.put("강릉 경포대", Arrays.asList("자연", "해변"));
        koreaLandmarks.put("서울시청", Arrays.asList("도시", "건축"));

        // 일본 랜드마크와 태그
        Map<String, List<String>> japanLandmarks = new HashMap<>();
        japanLandmarks.put("기온거리", Arrays.asList("전통", "문화"));
        japanLandmarks.put("후지산", Arrays.asList("자연", "산"));
        japanLandmarks.put("도쿄타워", Arrays.asList("현대 건축"));
        japanLandmarks.put("히로시마 원폭돔", Arrays.asList("유적지", "역사"));
        japanLandmarks.put("금각사", Arrays.asList("전통", "문화"));
        japanLandmarks.put("도쿄 황궁", Arrays.asList("역사", "전통"));
        japanLandmarks.put("하코네 온천", Arrays.asList("온천", "자연"));
        japanLandmarks.put("유후인 온천", Arrays.asList("온천", "자연"));
        japanLandmarks.put("홋카이도 자연경관", Arrays.asList("자연"));
        japanLandmarks.put("오사카성", Arrays.asList("역사", "전통"));
        japanLandmarks.put("아사쿠사 신사", Arrays.asList("전통", "유적지"));
        japanLandmarks.put("나라 공원", Arrays.asList("자연"));
        japanLandmarks.put("전통시장", Arrays.asList("문화", "전통시장"));
        japanLandmarks.put("미술관", Arrays.asList("미술관"));
        japanLandmarks.put("도쿄 스카이트리", Arrays.asList("전망", "도시"));
        japanLandmarks.put("오사카 구로몬 시장", Arrays.asList("쇼핑", "전통시장"));

        // 베트남 랜드마크와 태그
        Map<String, List<String>> vietnamLandmarks = new HashMap<>();
        vietnamLandmarks.put("하롱베이", Arrays.asList("해변", "자연"));
        vietnamLandmarks.put("다낭 해변", Arrays.asList("해변", "자연"));
        vietnamLandmarks.put("호이안 구시가지", Arrays.asList("역사", "전통"));
        vietnamLandmarks.put("미선 유적지", Arrays.asList("역사", "유적지"));
        vietnamLandmarks.put("전쟁박물관", Arrays.asList("역사", "전쟁 유적"));
        vietnamLandmarks.put("호치민시", Arrays.asList("도시", "문화"));
        vietnamLandmarks.put("다낭", Arrays.asList("도시", "해변"));
        vietnamLandmarks.put("푸꾸옥", Arrays.asList("해변", "자연"));
        vietnamLandmarks.put("호치민 시 푸마켓", Arrays.asList("전통시장", "문화"));
        vietnamLandmarks.put("하노이 구시가지", Arrays.asList("역사", "전통"));
        vietnamLandmarks.put("전쟁박물관", Arrays.asList("역사", "전쟁 유적"));
        vietnamLandmarks.put("미술관", Arrays.asList("미술관"));
        vietnamLandmarks.put("판시판", Arrays.asList("산", "모험"));
        vietnamLandmarks.put("다이롱강", Arrays.asList("자연"));
        vietnamLandmarks.put("푸마켓", Arrays.asList("전통시장", "문화"));
        vietnamLandmarks.put("전통 마을", Arrays.asList("전통", "문화"));

        // 랜드마크 데이터에 추가
        landmarkData.put("South Korea", koreaLandmarks);
        landmarkData.put("Japan", japanLandmarks);
        landmarkData.put("Vietnam", vietnamLandmarks);
    }

    public static Map<String, List<String>> getLandmarksByCountry(String country) {
        return landmarkData.get(country);
    }
}
