package reccomendationengine.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reccomendationengine.model.BrowserHistory;


@Repository
public interface BrowserHistoryRepository extends JpaRepository<BrowserHistory, String> {

	List<BrowserHistory> findByUseridOrderByAtTimeDesc(String userid, Pageable pageable);

	void deleteByProductidAndUserid(String productid, String userid);
	
	boolean existsByUserid(String userid);

}
