package al;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.*;


public class TravelAppGUI extends JFrame {

    private JComboBox<String> countryComboBox;
    private JList<String> tagList;
    private JPanel landmarkPanel;
    private JButton selectLandmarksButton;
    private List<String> selectedLandmarks;
    private Map<String, JCheckBox> landmarkCheckBoxes;

    public TravelAppGUI() {
        // 프레임 설정
        setTitle("여행지 추천 서비스");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 컴포넌트 설정
        countryComboBox = new JComboBox<>(new String[]{"South Korea", "Japan", "Vietnam"});
        tagList = new JList<>(new String[]{"역사", "문화", "자연", "전통", "해변", "산", "모험", "전망", "미술관", "쇼핑", "도시", "유적지"});
        landmarkPanel = new JPanel();
        landmarkPanel.setLayout(new BoxLayout(landmarkPanel, BoxLayout.Y_AXIS));
        selectLandmarksButton = new JButton("랜드마크 선택 완료");
        selectedLandmarks = new ArrayList<>();
        landmarkCheckBoxes = new HashMap<>();

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 태그 선택 리스트
        JPanel tagPanel = new JPanel();
        tagPanel.add(new JLabel("태그 선택:"));
        tagPanel.add(new JScrollPane(tagList));
        add(tagPanel, BorderLayout.WEST);

        // 나라 선택 ComboBox
        JPanel countryPanel = new JPanel();
        countryPanel.add(new JLabel("나라 선택:"));
        countryPanel.add(countryComboBox);
        add(countryPanel, BorderLayout.NORTH);

        // 랜드마크 체크박스 패널
        JPanel landmarkWrapperPanel = new JPanel(new BorderLayout());
        landmarkWrapperPanel.add(new JLabel("추천 랜드마크:"), BorderLayout.NORTH);
        landmarkWrapperPanel.add(new JScrollPane(landmarkPanel), BorderLayout.CENTER);
        landmarkWrapperPanel.add(selectLandmarksButton, BorderLayout.SOUTH);
        add(landmarkWrapperPanel, BorderLayout.CENTER);

        // 나라 변경 시 처리
        countryComboBox.addActionListener(e -> updateLandmarkList());

        // 태그 변경 시 처리
        tagList.addListSelectionListener(e -> updateLandmarkList());

        // 랜드마크 선택 버튼 이벤트 처리
        selectLandmarksButton.addActionListener(e -> handleLandmarkSelection());

        // 초기 데이터 설정
        LandmarkData.initializeData();
        updateLandmarkList();
    }

    private void updateLandmarkList() {
        String selectedCountry = (String) countryComboBox.getSelectedItem();
        List<String> selectedTags = tagList.getSelectedValuesList();

        Map<String, List<String>> countryLandmarks = LandmarkData.getLandmarksByCountry(selectedCountry);

        landmarkPanel.removeAll();
        landmarkCheckBoxes.clear();

        if (countryLandmarks != null) {
            for (Map.Entry<String, List<String>> entry : countryLandmarks.entrySet()) {
                String landmark = entry.getKey();
                List<String> tags = entry.getValue();

                // 태그가 일치하는 랜드마크만 추가
                if (!Collections.disjoint(tags, selectedTags)) {
                    JCheckBox checkBox = new JCheckBox(landmark);
                    landmarkCheckBoxes.put(landmark, checkBox);
                    landmarkPanel.add(checkBox);
                }
            }
        }

        // 패널 업데이트
        landmarkPanel.revalidate();
        landmarkPanel.repaint();
    }

    private void handleLandmarkSelection() {
        selectedLandmarks.clear();

        for (Map.Entry<String, JCheckBox> entry : landmarkCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selectedLandmarks.add(entry.getKey());
            }
        }

        if (selectedLandmarks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "최소 한 개 이상의 랜드마크를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "선택된 랜드마크:\n" + String.join(", ", selectedLandmarks),
                "선택 완료", JOptionPane.INFORMATION_MESSAGE);

            // 선택된 랜드마크 저장 이후 작업
            System.out.println("선택된 랜드마크: " + selectedLandmarks);
        }
    }

    public List<String> getSelectedLandmarks() {
        return selectedLandmarks;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelAppGUI().setVisible(true));
    }
}
