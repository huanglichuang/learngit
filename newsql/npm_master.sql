REPLACE INTO npm_master
SELECT a.*,b.province,b.isp
FROM npm_dwd a
LEFT JOIN zj_ipstore b
ON SUBSTR(a.ip,1,11) = b.ip
OR SUBSTR(a.ip,1,10) = b.ip
OR SUBSTR(a.ip,1,9) = b.ip
OR SUBSTR(a.ip,1,8) = b.ip
WHERE b.date between 20180917 AND 20180919
AND b.isp = '电信'
