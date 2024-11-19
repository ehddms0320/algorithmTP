import googlemaps
import pandas as pd

# Google Maps API 키 설정
API_KEY = 'AIzaSyAQ62izot9EIhYfDd-3kkKD5_OH-kEuzzc'  # 여기에 본인의 Google Maps API 키를 입력하세요
gmaps = googlemaps.Client(key=API_KEY)

# 랜드마크 데이터 (G10 국가)
landmarks = [
    # 미국 (USA)
    {'name': 'Statue of Liberty', 'location': 'Statue of Liberty, New York, USA'},
    {'name': 'Grand Canyon', 'location': 'Grand Canyon, Arizona, USA'},
    {'name': 'Yellowstone National Park', 'location': 'Yellowstone National Park, Wyoming, USA'},
    {'name': 'Mount Rushmore', 'location': 'Mount Rushmore, South Dakota, USA'},
    {'name': 'Times Square', 'location': 'Times Square, New York, USA'},
    {'name': 'Denali National Park', 'location': 'Denali National Park, Alaska, USA'},
    {'name': 'Hollywood Sign', 'location': 'Hollywood Sign, California, USA'},
    {'name': 'Alcatraz Island', 'location': 'Alcatraz Island, California, USA'},
    {'name': 'Niagara Falls', 'location': 'Niagara Falls, New York, USA'},
    {'name': 'United States Capitol', 'location': 'United States Capitol, Washington D.C., USA'},
    
    # 일본 (Japan)
    {'name': 'Mount Fuji', 'location': 'Mount Fuji, Shizuoka, Japan'},
    {'name': 'Tokyo Tower', 'location': 'Tokyo Tower, Tokyo, Japan'},
    {'name': 'Fushimi Inari Shrine', 'location': 'Fushimi Inari Shrine, Kyoto, Japan'},
    {'name': 'Himeji Castle', 'location': 'Himeji Castle, Hyogo, Japan'},
    {'name': 'Osaka Castle', 'location': 'Osaka Castle, Osaka, Japan'},
    {'name': 'Nara Park', 'location': 'Nara Park, Nara, Japan'},
    {'name': 'Shibuya Crossing', 'location': 'Shibuya Crossing, Tokyo, Japan'},
    {'name': 'Itsukushima Shrine', 'location': 'Itsukushima Shrine, Hiroshima, Japan'},
    {'name': 'Okinawa Churaumi Aquarium', 'location': 'Okinawa Churaumi Aquarium, Okinawa, Japan'},
    {'name': 'Roppongi Hills', 'location': 'Roppongi Hills, Tokyo, Japan'},
    
    # 독일 (Germany)
    {'name': 'Brandenburg Gate', 'location': 'Brandenburg Gate, Berlin, Germany'},
    {'name': 'Neuschwanstein Castle', 'location': 'Neuschwanstein Castle, Bavaria, Germany'},
    {'name': 'Cologne Cathedral', 'location': 'Cologne Cathedral, Cologne, Germany'},
    {'name': 'Reichstag Building', 'location': 'Reichstag Building, Berlin, Germany'},
    {'name': 'Black Forest', 'location': 'Black Forest, Baden-Württemberg, Germany'},
    {'name': 'Heidelberg Castle', 'location': 'Heidelberg Castle, Heidelberg, Germany'},
    {'name': 'Miniatur Wunderland', 'location': 'Miniatur Wunderland, Hamburg, Germany'},
    {'name': 'Europa Park', 'location': 'Europa Park, Rust, Germany'},
    {'name': 'Oktoberfest', 'location': 'Oktoberfest, Munich, Germany'},
    {'name': 'Zugspitze', 'location': 'Zugspitze, Bavaria, Germany'},
    
    # 영국 (United Kingdom)
    {'name': 'Big Ben', 'location': 'Big Ben, London, UK'},
    {'name': 'Stonehenge', 'location': 'Stonehenge, Wiltshire, UK'},
    {'name': 'Tower Bridge', 'location': 'Tower Bridge, London, UK'},
    {'name': 'Buckingham Palace', 'location': 'Buckingham Palace, London, UK'},
    {'name': 'Edinburgh Castle', 'location': 'Edinburgh Castle, Edinburgh, UK'},
    {'name': 'Lake District', 'location': 'Lake District, Cumbria, UK'},
    {'name': 'Windsor Castle', 'location': 'Windsor Castle, Windsor, UK'},
    {'name': 'The British Museum', 'location': 'The British Museum, London, UK'},
    {'name': 'The Roman Baths', 'location': 'The Roman Baths, Bath, UK'},
    {'name': 'Oxford University', 'location': 'Oxford University, Oxford, UK'},
    
    # 프랑스 (France)
    {'name': 'Eiffel Tower', 'location': 'Eiffel Tower, Paris, France'},
    {'name': 'Louvre Museum', 'location': 'Louvre Museum, Paris, France'},
    {'name': 'Mont Saint-Michel', 'location': 'Mont Saint-Michel, Normandy, France'},
    {'name': 'Palace of Versailles', 'location': 'Palace of Versailles, Versailles, France'},
    {'name': 'Notre-Dame Cathedral', 'location': 'Notre-Dame Cathedral, Paris, France'},
    {'name': 'Château de Chambord', 'location': 'Château de Chambord, Chambord, France'},
    {'name': 'Provence Lavender Fields', 'location': 'Provence Lavender Fields, Provence, France'},
    {'name': 'French Riviera', 'location': 'French Riviera, Nice, France'},
    {'name': 'Pont du Gard', 'location': 'Pont du Gard, Occitanie, France'},
    {'name': 'Arc de Triomphe', 'location': 'Arc de Triomphe, Paris, France'},
    
    # 이탈리아 (Italy)
    {'name': 'Colosseum', 'location': 'Colosseum, Rome, Italy'},
    {'name': 'Leaning Tower of Pisa', 'location': 'Leaning Tower of Pisa, Pisa, Italy'},
    {'name': 'Venice Canals', 'location': 'Venice Canals, Venice, Italy'},
    {'name': 'Vatican Museums', 'location': 'Vatican Museums, Vatican City, Italy'},
    {'name': 'Florence Cathedral', 'location': 'Florence Cathedral, Florence, Italy'},
    {'name': 'Cinque Terre', 'location': 'Cinque Terre, Liguria, Italy'},
    {'name': 'Pompeii', 'location': 'Pompeii, Naples, Italy'},
    {'name': 'Amalfi Coast', 'location': 'Amalfi Coast, Campania, Italy'},
    {'name': 'Milan Cathedral', 'location': 'Milan Cathedral, Milan, Italy'},
    {'name': 'Capri Island', 'location': 'Capri Island, Naples, Italy'},
    
    # 캐나다 (Canada)
    {'name': 'Niagara Falls', 'location': 'Niagara Falls, Ontario, Canada'},
    {'name': 'CN Tower', 'location': 'CN Tower, Toronto, Canada'},
    {'name': 'Banff National Park', 'location': 'Banff National Park, Alberta, Canada'},
    {'name': 'Stanley Park', 'location': 'Stanley Park, Vancouver, Canada'},
    {'name': 'Old Quebec', 'location': 'Old Quebec, Quebec City, Canada'},
    {'name': 'Whistler Blackcomb', 'location': 'Whistler Blackcomb, British Columbia, Canada'},
    {'name': 'Capilano Suspension Bridge', 'location': 'Capilano Suspension Bridge, Vancouver, Canada'},
    {'name': 'Peggy’s Cove', 'location': 'Peggy’s Cove, Nova Scotia, Canada'},
    {'name': 'Prince Edward Island', 'location': 'Prince Edward Island, Canada'},
    {'name': 'Bay of Fundy', 'location': 'Bay of Fundy, Nova Scotia, Canada'},
    
    # 네덜란드 (Netherlands)
    {'name': 'Keukenhof Gardens', 'location': 'Keukenhof Gardens, Lisse, Netherlands'},
    {'name': 'Anne Frank House', 'location': 'Anne Frank House, Amsterdam, Netherlands'},
    {'name': 'Van Gogh Museum', 'location': 'Van Gogh Museum, Amsterdam, Netherlands'},
    {'name': 'Rijksmuseum', 'location': 'Rijksmuseum, Amsterdam, Netherlands'},
    {'name': 'Kinderdijk Windmills', 'location': 'Kinderdijk Windmills, Kinderdijk, Netherlands'},
    {'name': 'Zaanse Schans', 'location': 'Zaanse Schans, Zaandam, Netherlands'},
    {'name': 'Maastricht Vrijthof', 'location': 'Maastricht Vrijthof, Maastricht, Netherlands'},
    {'name': 'Hoge Veluwe National Park', 'location': 'Hoge Veluwe National Park, Gelderland, Netherlands'},
    {'name': 'Euromast', 'location': 'Euromast, Rotterdam, Netherlands'},
    {'name': 'Delft', 'location': 'Delft, South Holland, Netherlands'},
    
    # 스웨덴 (Sweden)
    {'name': 'Vasa Museum', 'location': 'Vasa Museum, Stockholm, Sweden'},
    {'name': 'Stockholm Palace', 'location': 'Stockholm Palace, Stockholm, Sweden'},
    {'name': 'Drottningholm Palace', 'location': 'Drottningholm Palace, Stockholm, Sweden'},
    {'name': 'Abisko National Park', 'location': 'Abisko National Park, Lapland, Sweden'},
    {'name': 'Gothenburg Archipelago', 'location': 'Gothenburg Archipelago, Gothenburg, Sweden'},
    {'name': 'Icehotel', 'location': 'Icehotel, Jukkasjärvi, Sweden'},
    {'name': 'Liseberg', 'location': 'Liseberg, Gothenburg, Sweden'},
    {'name': 'Kalmar Castle', 'location': 'Kalmar Castle, Kalmar, Sweden'},
    {'name': 'Visby', 'location': 'Visby, Gotland, Sweden'},
    {'name': 'Öresund Bridge', 'location': 'Öresund Bridge, Malmö, Sweden'},
    
    # 스위스 (Switzerland)
    {'name': 'Matterhorn', 'location': 'Matterhorn, Zermatt, Switzerland'},
    {'name': 'Château de Chillon', 'location': 'Château de Chillon, Montreux, Switzerland'},
    {'name': 'Lake Geneva', 'location': 'Lake Geneva, Geneva, Switzerland'},
    {'name': 'Jungfraujoch', 'location': 'Jungfraujoch, Bernese Oberland, Switzerland'},
    {'name': 'Zurich Old Town', 'location': 'Zurich Old Town, Zurich, Switzerland'},
    {'name': 'Lucerne Chapel Bridge', 'location': 'Lucerne Chapel Bridge, Lucerne, Switzerland'},
    {'name': 'Rhine Falls', 'location': 'Rhine Falls, Schaffhausen, Switzerland'},
    {'name': 'Swiss National Park', 'location': 'Swiss National Park, Graubünden, Switzerland'},
    {'name': 'Bernina Express', 'location': 'Bernina Express, Graubünden, Switzerland'},
    {'name': 'Gruyères Castle', 'location': 'Gruyères Castle, Gruyères, Switzerland'}
]


# 랜드마크 이름 목록
landmark_names = [landmark['name'] for landmark in landmarks]

# 거리 행렬 초기화
distance_matrix = pd.DataFrame(index=landmark_names, columns=landmark_names)

# Google Maps API를 사용해 거리 계산
for i, origin in enumerate(landmarks):
    for j, destination in enumerate(landmarks):
        if i == j:
            distance_matrix.iloc[i, j] = 0  # 동일 랜드마크 간 거리는 0
        elif pd.isnull(distance_matrix.iloc[i, j]):
            try:
                result = gmaps.distance_matrix(origin['location'], destination['location'], mode='driving')
                distance = result['rows'][0]['elements'][0]['distance']['value']  # 거리 (미터)
                distance_matrix.iloc[i, j] = distance
                distance_matrix.iloc[j, i] = distance  # 대칭으로 저장
            except Exception as e:
                print(f"Error fetching distance between {origin['name']} and {destination['name']}: {e}")
                distance_matrix.iloc[i, j] = None
                distance_matrix.iloc[j, i] = None

# 거리 데이터를 distance_matrix.txt 파일에 저장
distance_matrix.to_csv('"C:/Users/danie/Desktop/환율변환기/rsc/distance_matrix.txt"', sep='\t', index=True)
print("거리 데이터가 distance_matrix.txt 파일에 저장되었습니다.")
