package net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Max on 11/28/16.
 */
public class RESTClient<T> {
    CloseableHttpClient _client;
    Gson _gson;
    JsonParser _parser;
    String _url;
    Type _objType;

    /**
     * Creates a new instance of RESTClient.
     * @param url the url to connect to
     */
    public RESTClient(String url, Type objType) {
        _client = HttpClients.createDefault();
        _gson = new Gson();
        _parser = new JsonParser();
        _url = url;
        _objType = objType;
    }

    /**
     * Sends a GET request for a specific child of the resource, and returns the result.
     * @param elem the child to retrieve
     * @return the resource requested, or null if unable to retrieve it
     */
    public T get(String elem) {
        HttpGet request = new HttpGet(_url + "/" + elem);
        ResponseHandler<T> rh = (httpResponse) -> {
                StatusLine header = httpResponse.getStatusLine();
                if (header.getStatusCode() >= 300) {
                    throw new HttpResponseException(header.getStatusCode(), header.getReasonPhrase());
                }
                Reader jsonResponse = new InputStreamReader(httpResponse.getEntity().getContent());
                return _gson.fromJson(jsonResponse, _objType);
            };
        try {
            return _client.execute(request, rh);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a GET request for the resource.
     * @return the resource requested, or null if unable to retrieve it
     */
    public List<T> get() {
        HttpGet request = new HttpGet(_url);
        ResponseHandler<List<T>> rh = (httpResponse) -> {
            StatusLine header = httpResponse.getStatusLine();
            if (header.getStatusCode() >= 300) {
                throw new HttpResponseException(header.getStatusCode(), header.getReasonPhrase());
            }
            Reader jsonResponse = new InputStreamReader(httpResponse.getEntity().getContent());
            JsonElement elemArray = _parser.parse(jsonResponse);
            if (elemArray.isJsonArray()) {
                List<T> results = new ArrayList<>();
                for (JsonElement e : elemArray.getAsJsonArray()) {
                    results.add(_gson.fromJson(e, _objType));
                }
                return results;
            }
            throw new IOException("JSON is not valid array");
        };
        try {
            return _client.execute(request, rh);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a POST request to add a new item.
     * @param item the object to add to the resource
     * @return true if successful
     */
    public boolean post(T item) {
        HttpPost request = new HttpPost(_url);
        String data = _gson.toJson(item, _objType);
        request.setEntity(new StringEntity(data,
                ContentType.create("application/json", "UTF-8")));
        CloseableHttpResponse response;
        try {
            response = _client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        StatusLine header = response.getStatusLine();
        if (header.getStatusCode() >= 300) {
            return false;
        }
        return true;
    }

    /**
     * Sends a POST request to add a new item, and handles the response.
     * @param item the object to add to the resource
     * @param rh the response handler
     * @param <R> return type of the response handler
     * @return the result of the response handler
     */
    public <R> R post(T item, ResponseHandler<R> rh) {
        HttpPost request = new HttpPost(_url);
        String data = _gson.toJson(item, _objType);
        request.setEntity(new StringEntity(data,
                ContentType.create("application/json", "UTF-8")));
        try {
            return _client.execute(request, rh);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a PUT request to modify a resource.
     * @param elem the child of the resource to modify
     * @param item data being pushed
     * @return true if successful
     */
    public boolean put(String elem, T item) {
        HttpPut request = new HttpPut(_url + "/" + elem);
        String data = _gson.toJson(item, _objType);
        request.setEntity(new StringEntity(data,
                ContentType.create("application/json", "UTF-8")));
        CloseableHttpResponse response;
        try {
            response = _client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        StatusLine header = response.getStatusLine();
        if (header.getStatusCode() >= 300) {
            return false;
        }
        return true;
    }

    /**
     * Sends a PUT request to modify a resource, and handles the response.
     * @param elem the child of the resource to modify
     * @param item data being pushed
     * @param rh the response handler
     * @param <R> return type of the response handler
     * @return result of the response handler
     */
    public <R> R put(String elem, T item, ResponseHandler<R> rh) {
        HttpPut request = new HttpPut(_url + "/" + elem);
        String data = _gson.toJson(item, _objType);
        request.setEntity(new StringEntity(data,
                ContentType.create("application/json", "UTF-8")));
        try {
            return _client.execute(request, rh);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String elem) {
        HttpDelete request = new HttpDelete(_url + "/" + elem);
        CloseableHttpResponse response;
        try {
            response = _client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        StatusLine header = response.getStatusLine();
        if (header.getStatusCode() >= 300) {
            return false;
        }
        return true;
    }

    public <R> R delete(String elem, ResponseHandler<R> rh) {
        HttpDelete request = new HttpDelete(_url + "/" + elem);
        try {
            return _client.execute(request, rh);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
