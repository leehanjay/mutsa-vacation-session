-- 초기 더미데이터
-- INSERT IGNORE: 서버 재시작 시 data.sql이 매번 재실행되어도 PK 중복 오류가 나지 않도록 함

INSERT IGNORE INTO users (user_id, email, password, nickname, credit, version, created_at, updated_at)
VALUES (1, 'mutsa_user@example.com', 'password123', '멋쟁이사자', 5000, 0, NOW(), NOW());

INSERT IGNORE INTO categories (category_id, tag_name, created_at, updated_at)
VALUES
    (1, '분식', NOW(), NOW()),
    (2, '기타', NOW(), NOW());

INSERT IGNORE INTO stores (store_id, category_id, store_name, store_rating, image_url, created_at, updated_at)
VALUES
    (1, 1, '엄마손분식', 4.5, 'https://example.com/images/store1.jpg', NOW(), NOW()),
    (2, 1, '행복도시락', 4.2, 'https://example.com/images/store2.jpg', NOW(), NOW()),
    (3, 2, '왕만두마을', 4.8, 'https://example.com/images/store3.jpg', NOW(), NOW());

INSERT IGNORE INTO menus (menu_id, store_id, menu_name, menu_price, created_at, updated_at)
VALUES
    (1, 1, '떡볶이', 3000, NOW(), NOW()),
    (2, 1, '순대', 3500, NOW(), NOW()),
    (3, 2, '치킨마요도시락', 6000, NOW(), NOW()),
    (4, 3, '왕만두', 5000, NOW(), NOW());

INSERT IGNORE INTO option_groups (option_group_id, menu_id, group_name, selection_type, is_required, created_at, updated_at)
VALUES
    (1, 1, '맛 선택', 'SINGLE', true, NOW(), NOW()),
    (2, 1, '토핑 추가', 'MULTIPLE', false, NOW(), NOW());

INSERT IGNORE INTO options (option_id, option_group_id, option_name, extra_price, created_at, updated_at)
VALUES
    (1, 1, '매운맛', 0, NOW(), NOW()),
    (2, 1, '순한맛', 0, NOW(), NOW()),
    (3, 2, '치즈 추가', 500, NOW(), NOW()),
    (4, 2, '소시지 추가', 1000, NOW(), NOW());
