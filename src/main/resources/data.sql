-- 초기 더미데이터
-- INSERT IGNORE: 서버 재시작 시 data.sql이 매번 재실행되어도 PK 중복 오류가 나지 않도록 함

INSERT IGNORE INTO users (user_id, email, password, nickname, credit, version, created_at, updated_at)
VALUES (1, 'mutsa_user@example.com', '$2a$10$7jxxMplM.E08QhjwPQDVhuWJ9frBd1LyiI5rrs0T8MnzYui3vEET.', '멋쟁이사자', 5000, 0, NOW(), NOW());

INSERT IGNORE INTO categories (category_id, tag_name, created_at, updated_at)
VALUES
    (1, '분식', NOW(), NOW()),
    (2, '양식', now(), now()),
    (3, '기타', NOW(), NOW());

INSERT IGNORE INTO stores (store_id, category_id, store_name, store_rating, image_url, created_at, updated_at)
VALUES
    (1, 1, '김가네', 4.5, 'https://example.com/images/store1.jpg', NOW(), NOW()),
    (2, 1, '오또김밥', 4.2, 'https://example.com/images/store2.jpg', NOW(), NOW()),
    (3, 3, '보용만두', 4.8, 'https://example.com/images/store3.jpg', NOW(), NOW()),
    (4, 2, '나폴리맛피자', 4.9, 'https://example.com/images/store4.jpg', now(), now()),
    (5, 2, '인앤아웃버거', 4.1, 'https://example.com/images/store5.jpg',now(), now()),
    (6, 2, '롤링파스타', 3.0, 'https://example.com/images/store6.jpg', now(), now()),
    (7, 3,'치폴레', 5.0, 'https://example.com/images/store7.jpg', now(), now()),
    (8, 3, '칸다소바', 4.3, 'https://example.com/images/store8.jpg', now(), now());

INSERT IGNORE INTO menus (menu_id, store_id, menu_name, menu_price, created_at, updated_at)
VALUES
    (1, 1, '참치김밥', 3500, NOW(), NOW()),
    (2, 1, '떡볶이', 3000, NOW(), NOW()),
    (3, 1, '라볶이', 4000, NOW(), NOW()),
    (4, 1, '순대', 3500, NOW(), NOW()),
    (5, 2, '오리지널김밥', 3000, NOW(), NOW()),
    (6, 2, '치즈김밥', 3500, NOW(), NOW()),
    (7, 2, '진미채김밥', 3800, NOW(), NOW()),
    (8, 2, '김치찌개', 6000, NOW(), NOW()),
    (9, 3, '왕만두', 5000, NOW(), NOW()),
    (10, 3, '고기만두', 4500, NOW(), NOW()),
    (11, 3, '김치만두', 4500, NOW(), NOW()),
    (12, 3, '물만두', 4000, NOW(), NOW()),
    (13, 4, '콤비네이션피자', 18000, NOW(), NOW()),
    (14, 4, '고구마피자', 19000, NOW(), NOW()),
    (15, 4, '페퍼로니피자', 17000, NOW(), NOW()),
    (16, 4, '포테이토피자', 18000, NOW(), NOW()),
    (17, 5, '치즈버거', 6500, NOW(), NOW()),
    (18, 5, '더블더블버거', 8500, NOW(), NOW()),
    (19, 5, '프렌치프라이', 3000, NOW(), NOW()),
    (20, 5, '밀크쉐이크', 4500, NOW(), NOW()),
    (21, 6, '로제파스타', 9900, NOW(), NOW()),
    (22, 6, '알리오올리오', 8900, NOW(), NOW()),
    (23, 6, '갈릭쉬림프파스타', 11900, NOW(), NOW()),
    (24, 6, '까르보나라', 9900, NOW(), NOW()),
    (25, 7, '치킨부리또', 9500, NOW(), NOW()),
    (26, 7, '스테이크보울', 11000, NOW(), NOW()),
    (27, 7, '치킨타코 3개', 9000, NOW(), NOW()),
    (28, 7, '과카몰리', 3000, NOW(), NOW()),
    (29, 8, '오리지널마제소바', 8500, NOW(), NOW()),
    (30, 8, '매운마제소바', 9000, NOW(), NOW()),
    (31, 8, '치즈마제소바', 9500, NOW(), NOW()),
    (32, 8, '교자만두', 4000, NOW(), NOW());

INSERT IGNORE INTO option_groups (option_group_id, menu_id, group_name, selection_type, is_required, created_at, updated_at)
VALUES
    (1, 2, '맵기 선택', 'SINGLE', true, NOW(), NOW()),
    (2, 2, '토핑 추가', 'MULTIPLE', false, NOW(), NOW()),
    (3, 13, '도우 선택', 'SINGLE', true, NOW(), NOW()),
    (4, 13, '사이즈 선택', 'SINGLE', true, NOW(), NOW()),
    (5, 17, '패티 선택', 'SINGLE', true, NOW(), NOW()),
    (6, 17, '세트 업그레이드', 'MULTIPLE', false, NOW(), NOW()),
    (7, 21, '면 선택', 'SINGLE', true, NOW(), NOW()),
    (8, 21, '토핑 추가', 'MULTIPLE', false, NOW(), NOW()),
    (9, 25, '라이스 선택', 'SINGLE', true, NOW(), NOW()),
    (10, 25, '살사 선택', 'SINGLE', true, NOW(), NOW()),
    (11, 25, '토핑 추가', 'MULTIPLE', false, NOW(), NOW()),
    (12, 29, '곱빼기 선택', 'SINGLE', true, NOW(), NOW()),
    (13, 29, '토핑 추가', 'MULTIPLE', false, NOW(), NOW());

INSERT IGNORE INTO options (option_id, option_group_id, option_name, extra_price, created_at, updated_at)
VALUES
    (1, 1, '순한맛', 0, NOW(), NOW()),
    (2, 1, '보통맛', 0, NOW(), NOW()),
    (3, 1, '매운맛', 0, NOW(), NOW()),
    (4, 2, '치즈 추가', 500, NOW(), NOW()),
    (5, 2, '쫄면사리 추가', 1000, NOW(), NOW()),
    (6, 3, '오리지널 도우', 0, NOW(), NOW()),
    (7, 3, '치즈크러스트', 3000, NOW(), NOW()),
    (8, 3, '고구마무스크러스트', 3000, NOW(), NOW()),
    (9, 4, '미디엄', 0, NOW(), NOW()),
    (10, 4, '라지', 4000, NOW(), NOW()),
    (11, 5, '비프패티', 0, NOW(), NOW()),
    (12, 5, '더블패티', 2000, NOW(), NOW()),
    (13, 6, '감자튀김 추가', 2000, NOW(), NOW()),
    (14, 6, '음료 추가', 1500, NOW(), NOW()),
    (15, 7, '스파게티', 0, NOW(), NOW()),
    (16, 7, '푸실리', 0, NOW(), NOW()),
    (17, 7, '페투치니', 500, NOW(), NOW()),
    (18, 8, '새우 추가', 2000, NOW(), NOW()),
    (19, 8, '베이컨 추가', 1500, NOW(), NOW()),
    (20, 9, '화이트라이스', 0, NOW(), NOW()),
    (21, 9, '브라운라이스', 0, NOW(), NOW()),
    (22, 10, '순한 살사', 0, NOW(), NOW()),
    (23, 10, '매운 살사', 0, NOW(), NOW()),
    (24, 11, '과카몰리 추가', 1500, NOW(), NOW()),
    (25, 11, '사워크림 추가', 500, NOW(), NOW()),
    (26, 12, '보통', 0, NOW(), NOW()),
    (27, 12, '곱빼기', 1500, NOW(), NOW()),
    (28, 13, '반숙란 추가', 1000, NOW(), NOW()),
    (29, 13, '차슈 추가', 2000, NOW(), NOW());
