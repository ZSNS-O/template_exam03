

package de.fhws.fiw.fds.suttondemo.client.web;

import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import de.fhws.fiw.fds.suttondemo.client.models.UniversityClientModel;
import okhttp3.Headers;

import java.util.Collection;

public class UniversityWebResponse extends WebApiResponse<UniversityClientModel> {

    public UniversityWebResponse(final Collection<UniversityClientModel> responseData, final Headers headers, final int lastStatusCode) {
        super(responseData, headers, lastStatusCode);
    }
}
