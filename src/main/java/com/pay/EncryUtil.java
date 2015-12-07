package com.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class EncryUtil {

	/**
	 * 生成RSA签名
	 */
	public static String handleRSA(TreeMap<String, Object> map,
			String privateKey) {
		StringBuffer sbuffer = new StringBuffer();
		for (Entry<String, Object> entry : map.entrySet()) {
			sbuffer.append(entry.getValue());
		}
		String signTemp = sbuffer.toString();
		String sign = "";
		if (StringUtils.isNotEmpty(privateKey)) {
			sign = RSA.sign(signTemp, privateKey);
		}
		return sign;
	}

	/**
	 * 对支付返回的结果进行验签
	 * 
	 * @param data 数据密文
	 * @param encrypt_key 对AesKey加密后的密文
	 * @param publickKey 提供的公钥
	 * @param privateKey 自己的私钥
	 * @return 验签是否通过
	 * @throws Exception
	 */
	public static TreeMap<String, String> checkDecryptAndSign(String data, String encrypt_key,String publickKey, String privateKey) throws Exception {

		/** 1.使用merchantPrivateKey解开aesEncrypt。 */
		String AESKey = RSA.decrypt(encrypt_key, privateKey);
		
		/** 2.用aeskey解开data。取得data明文 */
		String realData = AES.decryptFromBase64(data, AESKey);
		TreeMap<String, String> map = JSON.parseObject(realData,new TypeReference<TreeMap<String, String>>() {});

		/** 3.取得data明文sign。 */
		String sign = StringUtils.trimToEmpty(map.get("sign"));

		/** 4.对map中的值进行验证 */
		StringBuffer signData = new StringBuffer();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();

			/** 把sign参数隔过去 */
			if (StringUtils.equals((String) entry.getKey(), "sign")) {
				continue;
			}
			signData.append(entry.getValue() == null ? "" : entry.getValue());
		}
		/** 5. result为true时表明验签通过 */
		boolean result = RSA.checkSign(signData.toString(), sign,publickKey);
		if (!result) {
			return null;
		}
		return map;
	}
	
}
