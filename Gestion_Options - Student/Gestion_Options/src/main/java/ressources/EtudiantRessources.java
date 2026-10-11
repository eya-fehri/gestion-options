package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
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

@Path("etudiants")
public class EtudiantRessources {

    private final EtudiantBusiness etudiantBusiness = Services.ETUDIANTS;
    private final OptionBusiness optionBusiness = Services.OPTIONS;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (etudiantBusiness.addEtudiant(etudiant)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.ok(etudiantBusiness.getAllEtudiants()).build();
    }

    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> liste = etudiantBusiness.getEtudiantsByOption(option);
        return Response.ok(new EtudiantList(liste)).build();
    }

    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (etudiant != null && etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}