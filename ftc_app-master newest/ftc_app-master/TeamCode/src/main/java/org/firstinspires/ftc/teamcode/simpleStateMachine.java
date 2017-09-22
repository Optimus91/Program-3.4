package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@Disabled
@Autonomous(name="State Machine Template", group="Workshop")

public class simpleStateMachine extends OpMode
{
    int state=0;
    public simpleStateMachine()
    {
    }
    @Override public void init(){
       //Hardwaremap goes here
    }
    @Override public void start(){

    }


    @Override public void loop ()
    {

        switch (state) {
            case 0:
                //case 0 code goes here
                state = 1;
                break;
            case 1:
                //case 1 code goes here
                state = 2;
                break;
            case 2:
                //case 2 code goes here
                state = 3;
                break;
            case 3:
                //case 3 code goes here
                state = 4;
                break;


            default: //if variable "state" is not used in any of the above case statements, default case is run (ie: state=4)
                break;
        }


    }
}