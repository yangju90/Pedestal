package indi.mat.work.project;

import indi.mat.work.project.model.system.SystemMenu;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setHerf("adadadadad");
        System.out.println(systemMenu);
        System.out.println(systemMenu.getHerf());

        assertTrue( true );
    }
}
