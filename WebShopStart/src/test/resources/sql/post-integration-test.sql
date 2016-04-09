DELETE FROM customer WHERE name='John' AND lastname='Doe' AND password='1234' AND username='jdoe';

DELETE FROM product WHERE details='Lecker!' AND name='Pizza' AND price=6.5 AND category_id=(SELECT id FROM category WHERE name='Alkohol');

DELETE FROM category WHERE name='Alkohol';
