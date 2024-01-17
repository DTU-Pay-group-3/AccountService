package accountfacade.service.adapter.rest;

import accountfacade.service.AccountFacadeService;
import accountfacade.service.DTUPayAccount;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
public class AccountsResource {
    // Services
    AccountFacadeService accountsService = new AccountFacadeFactory().getService();

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DTUPayAccount getAccount(@PathParam("id") String id) {
        return accountsService.getAccount(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DTUPayAccount registerAccount(DTUPayAccount account) {
        return accountsService.register(account);
    }
}
