package org.fogbowcloud.arrebol.execution.dockerworker;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.fogbowcloud.arrebol.utils.AppUtil;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ContainerRequestHelper {
    private HttpWrapper httpWrapper;
    private String address;
    private String image;
    private String containerName;
    private Map<String, String> requirements;

    private final Logger LOGGER = Logger.getLogger(ContainerRequestHelper.class);

    public ContainerRequestHelper(String address, String containerName, String image) {
        this.httpWrapper = new HttpWrapper();
        this.address = address;
        this.containerName = containerName;
        this.image = image;
        this.requirements = new HashMap<>();
    }

    public String createContainer() throws Exception {
        final String endPoint = String.format("%s/containers/create?name=%s", address, containerName);
        StringEntity body = jsonCreateContainer();
        String response = this.httpWrapper.doRequest(HttpPost.METHOD_NAME, endPoint, body);
        String containerId = AppUtil.getValueFromJsonStr("Id", response);
        return containerId;
    }

    public void startContainer() throws Exception {
        final String endpoint = String.format("%s/containers/%s/start", address, containerName);
        post(endpoint);
    }

    public void killContainer() throws Exception {
        final String endpoint = String.format("%s/containers/%s/kill", address, containerName);
        post(endpoint);
    }

    public void removeContainer() throws Exception {
        final String endpoint = String.format("%s/containers/%s", address, containerName);
        this.httpWrapper.doRequest(HttpDelete.METHOD_NAME, endpoint);
    }

    private void post(String endpoint) throws Exception {
        this.httpWrapper.doRequest(HttpPost.METHOD_NAME, endpoint);
    }

    public void setRequirements(Map<String, String> requirements) {
        this.requirements = requirements;
    }

    private StringEntity jsonCreateContainer() throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        AppUtil.makeBodyField(jsonObject, "Image", image);
        AppUtil.makeBodyField(jsonObject, "Tty", true);
        AppUtil.makeBodyField(jsonObject, "HostConfig", requirements);
        return new StringEntity(jsonObject.toString());
    }

}
