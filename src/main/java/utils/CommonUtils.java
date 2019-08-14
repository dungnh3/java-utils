package utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * @author dungnh3
 * @since Aug 14th 2019
 */
public class CommonUtils {

    public static <E> List<E> toList(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if (iterable != null) {
            for (E e : iterable) {
                list.add(e);
            }
        }
        return list;
    }

    public static <E> Set<E> toHashSet(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (Set<E>) iterable;
        }
        Set<E> list = new HashSet<E>();
        if (iterable != null) {
            for (E e : iterable) {
                list.add(e);
            }
        }
        return list;
    }

    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    public static int byteArrayToInt(byte[] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }

    public static String sendPost(List<NameValuePair> nvps, String postUrl, int timeout) throws UnsupportedEncodingException, IOException {
        String result;
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setStaleConnectionCheckEnabled(true)
                .build();
        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed : HTTP getStatusCode: "
                            + response.getStatusLine().getStatusCode() + " HTTP getReasonPhrase: " + response.getStatusLine().getReasonPhrase());
                }
                HttpEntity entity = response.getEntity();
                try (InputStream inputStream = entity.getContent()) {
                    result = IOUtils.toString(inputStream, "UTF-8");
                }
            }
        }
        return result;
    }

    public static String sendPostJson(String postUrl, String jsonContent, int timeout)
            throws Exception {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {
            HttpPost httpPost = new HttpPost(postUrl);
            StringEntity input = new StringEntity(jsonContent, "UTF-8");
            input.setContentType("application/json");
            httpPost.setEntity(input);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed : HTTP getStatusCode: "
                            + response.getStatusLine().getStatusCode()
                            + " HTTP getReasonPhrase: "
                            + response.getStatusLine().getReasonPhrase());
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())))) {
                    String output;
                    StringBuilder strBuilder = new StringBuilder();
                    while ((output = br.readLine()) != null) {
                        strBuilder.append(output);
                    }
                    return strBuilder.toString();
                }
            }
        }
    }

    public static String sendPostJsonViaProxy(String postUrl, String jsonContent, String proxyHost, int proxyPort, int timeout)
            throws Exception {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setStaleConnectionCheckEnabled(true)
                .setProxy(proxy)
                .build();
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {
            HttpPost httpPost = new HttpPost(postUrl);
            StringEntity input = new StringEntity(jsonContent, "UTF-8");
            input.setContentType("application/json");
            httpPost.setEntity(input);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed : HTTP getStatusCode: "
                            + response.getStatusLine().getStatusCode()
                            + " HTTP getReasonPhrase: "
                            + response.getStatusLine().getReasonPhrase());
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())))) {
                    String output;
                    StringBuilder strBuilder = new StringBuilder();
                    while ((output = br.readLine()) != null) {
                        strBuilder.append(output);
                    }
                    return strBuilder.toString();
                }
            }
        }
    }

    public static String sendGet(String url, int timeout) throws IOException {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setStaleConnectionCheckEnabled(true)
                .build();

        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpclient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed : HTTP getStatusCode: "
                            + response.getStatusLine().getStatusCode()
                            + " HTTP getReasonPhrase: "
                            + response.getStatusLine().getReasonPhrase()
                            + " content " + response.toString()
                    );
                }
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                }
            }
        }
    }

    public static String getParameterAsString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder(request.getRequestURL());
        Enumeration enu = request.getParameterNames();
        stringBuilder.append("?");
        while (enu.hasMoreElements()) {
            String paramName = (String) enu.nextElement();
            stringBuilder.append(paramName).append("=")
                    .append(request.getParameter(paramName))
                    .append("&");
        }
        return stringBuilder.toString();
    }

    public static String sendPostViaProxy(List<NameValuePair> nvps, String postUrl, String proxyHost, int proxyPort, int timeout) throws IOException {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setStaleConnectionCheckEnabled(true)
                .setProxy(proxy)
                .build();
        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed : HTTP getStatusCode: "
                            + response.getStatusLine().getStatusCode()
                            + " HTTP getReasonPhrase: "
                            + response.getStatusLine().getReasonPhrase()
                            + " content " + response.toString()
                    );
                }
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                ;
                String resp = IOUtils.toString(inputStream, "UTF-8");
                return resp;
            }
        }
    }

    public static String sendGetViaProxy(String url, String proxyHost, int proxyPort, int timeout) throws IOException {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setStaleConnectionCheckEnabled(true)
                .setProxy(proxy)
                .build();
        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpclient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Failed : HTTP getStatusCode: "
                            + response.getStatusLine().getStatusCode()
                            + " HTTP getReasonPhrase: "
                            + response.getStatusLine().getReasonPhrase());
                }
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                }
            }
        }
    }
}
