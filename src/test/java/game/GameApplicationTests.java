package game;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameApplicationTests {

	@Autowired
	private Set<RoleModifyLogic> rolesLogics;
	@Test
	void contextLoads() {
		int roleType=1;
		rolesLogics.forEach(logic->{
			int type=logic.roleType();
			if(type==roleType)
				logic.modify();
		});
	}

}
