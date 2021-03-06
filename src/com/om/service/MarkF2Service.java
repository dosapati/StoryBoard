package com.om.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: dosapati
 * Date: 11/9/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Path("/chart")
public class MarkF2Service {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/for/{symbol}")
    public String chartFor(@PathParam("symbol")String symbol){
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://dev.markitondemand.com/Api/v2/InteractiveChart/json");
        //.resource("http://betawebapi.dowjones.com/fintech/articles/api/v1/source/424/?count=20");

        ClientResponse response = webResource.queryParam("parameters","{\"Normalized\":false,\"NumberOfDays\":365,\"DataPeriod\":\"Day\",\"Elements\":[{\"Symbol\":\"AAPL\",\"Type\":\"price\",\"Params\":[\"c\"]}]}").header("Content-Type", "application/json").accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        Gson gson = new Gson();

        String output = response.getEntity(String.class);
        return output;
    }

}
