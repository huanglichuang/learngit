REPLACE INTO zj_dmp_user
SELECT a.*,b.province,b.city,b.isp
FROM zj_user a
LEFT JOIN user_phone_area b
ON a.uid = b.uid
