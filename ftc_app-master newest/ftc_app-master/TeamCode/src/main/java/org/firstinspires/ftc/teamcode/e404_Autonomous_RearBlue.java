package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by kalee1 on 11/24/17.
 */
@Autonomous(name="Blue Jewel Rear NEW", group="Jewel")

public class e404_Autonomous_RearBlue extends e404_Autonomous
{
    @Override
    public void init()
    {
        this.allianceColor = "BLUE";
        this.alliancePos = "REAR";
        super.init();
    }


}
