package ressources;

import entities.Option;
import metiers.OptionBusiness;
import utilities.Services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class OptionRessources {

    private final OptionBusiness optionBusiness = Services.OPTIONS;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (option != null && optionBusiness.addOption(option)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> liste;
        if (domaine == null || domaine.trim().isEmpty()) {
            liste = optionBusiness.getListeOptions();
        } else {
            liste = optionBusiness.getOptionsByDomaine(domaine);
        }
        return Response.ok(liste).build();
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOption(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }


    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option != null && optionBusiness.updateOption(code, option)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}