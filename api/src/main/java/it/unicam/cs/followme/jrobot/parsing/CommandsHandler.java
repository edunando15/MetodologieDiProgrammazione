package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.util.Pair;
import it.unicam.cs.followme.utilities.FollowMeParserHandler;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.List;

/**
 * This interface extends the {@link FollowMeParserHandler} interface, introducing
 * new functionalities. Classes implementing this interface
 * will have the same characteristics of a {@link FollowMeParserHandler}; in
 * addiction they will give the possibility to obtain the commands read in the
 * correct order.
 */
public interface CommandsHandler extends FollowMeParserHandler {

    /**
     * This method is used to obtain the commands read by
     * the Handler.
     * @return A List containing pairs of commands and
     * their parameters.
     */
    List<Pair<RobotCommand, String[]>> getReadCommands();

}
