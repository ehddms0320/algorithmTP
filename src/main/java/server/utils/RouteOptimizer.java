package server.utils;

import server.models.Landmark;
import server.models.Airport;
import server.models.Route;
import java.util.*;
import java.util.stream.Collectors;

public class RouteOptimizer {
    public Route findOptimalRoute(List<Landmark> landmarks, Airport startAirport, List<String> tags, int maxLandmarks) {
        try {
            // 태그 기반 필터링
            List<Landmark> filteredLandmarks = landmarks.stream()
                .filter(l -> tags.stream().anyMatch(tag -> l.getTags().contains(tag)))
                .limit(maxLandmarks)
                .collect(Collectors.toList());

            System.out.println("Filtered Landmarks (" + filteredLandmarks.size() + "): " + 
                filteredLandmarks.stream().map(Landmark::getName).collect(Collectors.joining(", ")));

            if (filteredLandmarks.isEmpty()) {
                System.out.println("No landmarks match the given tags.");
                return new Route(new ArrayList<>(), 0.0);
            }

            // 시작 공항을 랜드마크로 변환
            Landmark startAirportLandmark = new Landmark(0L, startAirport.getName(), startAirport.getCountry(), 
                "Airport", new ArrayList<>(), startAirport.getLatitude(), startAirport.getLongitude());
        
            // 노드 초기화 및 검증
            Set<Landmark> allNodes = new HashSet<>(filteredLandmarks);
            allNodes.add(startAirportLandmark);
        
            System.out.println("Total nodes to process: " + allNodes.size());

            // 거리 및 이전 노드 맵 초기화
            Map<Landmark, Double> distances = new HashMap<>();
            Map<Landmark, Landmark> previousNodes = new HashMap<>();
            Set<Landmark> visited = new HashSet<>();
            Map<Landmark, Boolean> inQueue = new HashMap<>();

            // 초기화
            for (Landmark landmark : allNodes) {
                distances.put(landmark, Double.MAX_VALUE);
                previousNodes.put(landmark, null);
                inQueue.put(landmark, false);
            }
            distances.put(startAirportLandmark, 0.0);
            inQueue.put(startAirportLandmark, true);

            // 다익스트라 알고리즘 구현
            PriorityQueue<Landmark> queue = new PriorityQueue<>(
                (a, b) -> Double.compare(distances.get(a), distances.get(b)));
            queue.offer(startAirportLandmark);

            System.out.println("\nStarting path finding from: " + startAirport.getName());

            while (!queue.isEmpty()) {
                Landmark current = queue.poll();
                inQueue.put(current, false);
            
                if (visited.contains(current)) {
                    continue;
                }
            
                visited.add(current);
                double currentDistance = distances.get(current);
            
                System.out.println("\nProcessing: " + current.getName() + 
                    " (Distance: " + String.format("%.2f", currentDistance) + ")");

                // 모든 이웃 노드에 대해 최단 경로 갱신
                for (Landmark neighbor : allNodes) {
                    if (visited.contains(neighbor)) {
                        continue;
                    }

                    try {
                        double edgeDistance = current.calculateDistance(neighbor);
                    
                        // 거리 유효성 검사
                        if (edgeDistance < 0 || Double.isInfinite(edgeDistance) || Double.isNaN(edgeDistance)) {
                            System.out.println("Warning: Invalid distance between " + 
                                current.getName() + " and " + neighbor.getName());
                            continue;
                        }

                        double newDistance = currentDistance + edgeDistance;
                        double existingDistance = distances.get(neighbor);

                        if (newDistance < existingDistance) {
                            System.out.println("Found better path to " + neighbor.getName() + 
                                " via " + current.getName() + 
                                " (New: " + String.format("%.2f", newDistance) + 
                                ", Old: " + String.format("%.2f", existingDistance) + ")");
                        
                            distances.put(neighbor, newDistance);
                            previousNodes.put(neighbor, current);
                        
                            // 큐 업데이트
                            if (!inQueue.get(neighbor)) {
                                queue.offer(neighbor);
                                inQueue.put(neighbor, true);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Warning: Failed to process path to " + neighbor.getName());
                    }
                }
            }

            // 방문 상태 및 거리 정보 출력
            System.out.println("\nPath finding completed.");
            System.out.println("Visited nodes: " + visited.size() + "/" + allNodes.size());
            System.out.println("Distances computed:");
            distances.forEach((node, dist) -> {
                System.out.println(node.getName() + ": " + 
                    (dist == Double.MAX_VALUE ? "UNREACHABLE" : String.format("%.2f", dist)));
            });

            // 모든 랜드마크에 대한 경로 재구성
            System.out.println("\nReconstructing paths for all landmarks:");
            
            List<Landmark> optimalRoute = new ArrayList<>();
            Set<Landmark> visitedInPath = new HashSet<>();
            Map<Landmark, List<Landmark>> allPaths = new HashMap<>();
            
            // 시작점 추가
            optimalRoute.add(startAirportLandmark);
            visitedInPath.add(startAirportLandmark);

            // 거리에 따라 랜드마크 정렬
            List<Landmark> sortedLandmarks = filteredLandmarks.stream()
                .filter(l -> !l.equals(startAirportLandmark))
                .filter(l -> visited.contains(l))
                .filter(l -> distances.get(l) < Double.MAX_VALUE)
                .sorted((a, b) -> Double.compare(distances.get(a), distances.get(b)))
                .collect(Collectors.toList());

            if (sortedLandmarks.isEmpty()) {
                System.out.println("No reachable landmarks found.");
                return new Route(new ArrayList<>(), 0.0);
            }

            // 각 랜드마크에 대한 경로 구성
            for (Landmark landmark : sortedLandmarks) {
                List<Landmark> currentPath = new ArrayList<>();
                Landmark current = landmark;
                
                // 경로 역추적
                while (current != null && !visitedInPath.contains(current)) {
                    currentPath.add(current);
                    current = previousNodes.get(current);
                }
                
                if (!currentPath.isEmpty()) {
                    Collections.reverse(currentPath);
                    allPaths.put(landmark, currentPath);
                    
                    System.out.println("Path to " + landmark.getName() + ": " + 
                        currentPath.stream().map(Landmark::getName).collect(Collectors.joining(" -> ")));
                }
            }

            // 최적 경로 구성
            for (Landmark landmark : sortedLandmarks) {
                List<Landmark> path = allPaths.get(landmark);
                if (path != null) {
                    for (Landmark node : path) {
                        if (!visitedInPath.contains(node)) {
                            optimalRoute.add(node);
                            visitedInPath.add(node);
                        }
                    }
                }
            }

            // 경로 유효성 검사
            if (optimalRoute.size() < 2) {
                System.out.println("Error: Route must contain at least two points");
                return new Route(new ArrayList<>(), 0.0);
            }

            // 최종 거리 계산
            double totalDistance = 0.0;
            for (int i = 0; i < optimalRoute.size() - 1; i++) {
                try {
                    Landmark currentNode = optimalRoute.get(i);
                    Landmark nextNode = optimalRoute.get(i + 1);
                    double segmentDistance = currentNode.calculateDistance(nextNode);
                    
                    if (Double.isNaN(segmentDistance) || Double.isInfinite(segmentDistance)) {
                        System.out.println("Error: Invalid distance between " + 
                            currentNode.getName() + " and " + nextNode.getName());
                        return new Route(new ArrayList<>(), 0.0);
                    }
                    
                    totalDistance += segmentDistance;
                    
                    // 경로 연속성 검증
                    double expectedDistance = distances.get(nextNode) - distances.get(currentNode);
                    if (Math.abs(expectedDistance - segmentDistance) > 0.001) {
                        System.out.println("Warning: Distance inconsistency in segment " + 
                            currentNode.getName() + " -> " + nextNode.getName() +
                            " (Expected: " + String.format("%.2f", expectedDistance) + 
                            ", Actual: " + String.format("%.2f", segmentDistance) + ")");
                    }
                } catch (Exception e) {
                    System.out.println("Error: Failed to calculate segment distance: " + e.getMessage());
                    return new Route(new ArrayList<>(), 0.0);
                }
            }

            // 최종 경로 출력
            System.out.println("\nFinal Route:");
            System.out.println("Path: " + optimalRoute.stream()
                .map(Landmark::getName)
                .collect(Collectors.joining(" -> ")));
            System.out.println("Total Distance: " + String.format("%.2f", totalDistance) + " km");
            System.out.println("Landmarks visited: " + (optimalRoute.size() - 1));

            return new Route(optimalRoute, totalDistance);
        
        } catch (Exception e) {
            System.out.println("Error: Failed to compute optimal route: " + e.getMessage());
            e.printStackTrace();  
            return new Route(new ArrayList<>(), 0.0);
        }
    }

    private Landmark findNearestToAirport(Airport airport, List<Landmark> landmarks) {
        if (landmarks.isEmpty()) {
            throw new RuntimeException("No landmarks available");
        }

        Landmark nearest = landmarks.get(0);
        double minDistance = airport.calculateDistance(nearest);

        for (int i = 1; i < landmarks.size(); i++) {
            Landmark landmark = landmarks.get(i);
            double distance = airport.calculateDistance(landmark);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = landmark;
            }
        }

        return nearest;
    }

    private void findAllPaths(Landmark current, List<Landmark> path, List<List<Landmark>> allPaths, Map<Landmark, List<Landmark>> previousNodes) {
        path.add(current);
        if (!previousNodes.containsKey(current) || previousNodes.get(current).isEmpty()) {
            allPaths.add(new ArrayList<>(path));
        } else {
            for (Landmark prev : previousNodes.get(current)) {
                findAllPaths(prev, path, allPaths, previousNodes);
            }
        }
        path.remove(path.size() - 1);
    }
}
