package algo;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Map;

public class TravelAppGUI extends JFrame {

    private JComboBox<String> countryComboBox;
    private JList<String> tagList;
    private DefaultListModel<String> landmarkListModel;
    private JList<String> landmarkList;
    private List<String> selectedLandmarks = new ArrayList<>();  // 사용자가 선택한 랜드마크 리스트

    public TravelAppGUI() {
        // 프레임 설정
        setTitle("여행지 추천 서비스");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 컴포넌트 설정
        countryComboBox = new JComboBox<>(new String[]{"South Korea", "Japan", "Vietnam"});
        tagList = new JList<>(new String[]{"역사", "문화", "자연", "전통", "해변", "산", "모험", "전망", "미술관", "쇼핑", "도시", "유적지"});
        landmarkListModel = new DefaultListModel<>();
        landmarkList = new JList<>(landmarkListModel);

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 태그 선택 리스트
        JPanel panel = new JPanel();
        panel.add(new JLabel("태그 선택:"));
        panel.add(new JScrollPane(tagList));
        add(panel, BorderLayout.WEST);

        // 나라 선택 ComboBox
        JPanel countryPanel = new JPanel();
        countryPanel.add(new JLabel("나라 선택:"));
        countryPanel.add(countryComboBox);
        add(countryPanel, BorderLayout.NORTH);

        // 랜드마크 리스트
        JPanel landmarkPanel = new JPanel();
        landmarkPanel.add(new JLabel("추천 랜드마크:"));
        landmarkPanel.add(new JScrollPane(landmarkList));
        add(landmarkPanel, BorderLayout.CENTER);

        // 나라 변경 시 처리
        countryComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateLandmarkList();
            }
        });

        // 태그 변경 시 처리
        tagList.addListSelectionListener(e -> updateLandmarkList());

        // 랜드마크 선택 시 체크박스
        landmarkList.addListSelectionListener(e -> {
            selectedLandmarks = landmarkList.getSelectedValuesList();
        });

        // 초기 데이터 설정
        LandmarkData.initializeData();
        updateLandmarkList();
    }

    private void updateLandmarkList() {
        String selectedCountry = (String) countryComboBox.getSelectedItem();
        List<String> selectedTags = tagList.getSelectedValuesList();

        Map<String, List<String>> countryLandmarks = LandmarkData.getLandmarksByCountry(selectedCountry);
        landmarkListModel.clear();

        if (countryLandmarks != null) {
            for (Map.Entry<String, List<String>> entry : countryLandmarks.entrySet()) {
                String landmark = entry.getKey();
                List<String> tags = entry.getValue();

                // 태그가 일치하는 랜드마크만 추가
                if (!Collections.disjoint(tags, selectedTags)) {
                    landmarkListModel.addElement(landmark);
                }
            }
        }
    }

    private List<LatLng> getLandmarkLocations() {
        List<LatLng> locations = new ArrayList<>();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("YOUR_GOOGLE_API_KEY")  // 구글 API 키를 여기에 삽입하세요.
                .build();

        try {
            for (String landmark : selectedLandmarks) {
                GeocodingResult[] results = GeocodingApi.geocode(context, landmark).await();
                if (results.length > 0) {
                    // 위치 정보 추가 (위도, 경도)
                    locations.add(results[0].geometry.location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

    public void findOptimalRoute() {
        List<LatLng> locations = getLandmarkLocations();
        if (locations.size() < 2) {
            System.out.println("경로를 계산하기에 충분한 랜드마크가 선택되지 않았습니다.");
            return;
        }

        // 다익스트라 알고리즘을 통해 최적 경로 계산 (단순 예시로 위치 간의 거리 계산)
        // 실제 다익스트라 알고리즘을 적용하려면 거리 계산 로직을 추가해야 합니다.
        System.out.println("선택된 랜드마크들의 최적 경로 계산 중...");
        for (LatLng location : locations) {
            System.out.println("위치: " + location.lat + ", " + location.lng);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelAppGUI().setVisible(true));
    }
}
