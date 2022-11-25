package org.streamReaderApp.viewproductmessageconsumer;

import org.springframework.data.repository.CrudRepository;
import org.streamReaderApp.model.BrowserHistory;

public interface CrudRepositoryForBrowserHistory extends CrudRepository<BrowserHistory, String>{

}
