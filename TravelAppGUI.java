package al;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

public class TravelAppGUI extends JFrame {

    private JComboBox<String> countryComboBox;
    private JList<String> tagList;
    private DefaultListModel<String> landmarkListModel;
    private JList<String> landmarkList;
    private List<String> selectedLandmarks;

    private static final String API_KEY = "YOUR_GOOGLE_MAPS_API_KEY"; // Google Maps API Key

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
        selectedLandmarks = new ArrayList<>();

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

        // 랜드마크 선택 시 처리
        landmarkList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedLandmarks();
            }
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

    private void updateSelectedLandmarks() {
        selectedLandmarks.clear();
        for (String landmark : landmarkList.getSelectedValuesList()) {
            selectedLandmarks.add(landmark);
        }

        // 선택한 랜드마크들의 위치 정보 받아오기
        getLocations(selectedLandmarks);
    }

    private void getLocations(List<String> landmarks) {
        for (String landmark : landmarks) {
            try {
                String location = getLocationFromGoogleMaps(landmark);
                System.out.println(landmark + " 위치: " + location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getLocationFromGoogleMaps(String landmark) throws Exception {
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + landmark + "&key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 응답 파싱
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject location = jsonResponse.getJSONArray("results")
                                          .getJSONObject(0)
                                          .getJSONObject("geometry")
                                          .getJSONObject("location");

        double lat = location.getDouble("lat");
        double lng = location.getDouble("lng");

        return "위도: " + lat + ", 경도: " + lng;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelAppGUI().setVisible(true));
    }
}
