package com.mygdx.wargame.pilot;

public enum Perks {

    MissileExpert("+5% to missile hit chance", "Missile expert"),
    PlasmaExpert("+5% to plasma weapon hit chance", "Plasma expert"),
    BallisticsExpert("+5% to ballistic weapon hit chance", "Ballistic expert"),
    IonWeaponExpert("+5% to ion weapon hit chance", "Ion expert"),
    FlamerExpert("+5% to flamer hit chance", "Flamer expert"),
    LaserExpert("+5% to laser weapon hut chance", "Laser expert"),
    Agile("+5 evasion against weapons", "Agile"),
    Robust("-5% weapon damage received for mech", "Robust"),
    ExpertPilot("+1 movement points for mech", "Expert pilot"),
    Cautious("-10 to morale saves,  +1 to all weapon ranges", "Cautious"),
    Brave("+10 to all morale saves", "Brave"),
    Crazy("No morale saves, always fights", "Crazy"),
    Lucky("+3%  to critical chances with weapons", "Lucky"),
    Leader("+10  to morale saves for the team", "Leader"),
    Hero("+20 to morale saves for the team", "Leader"),
    Dreaded("-10 morale saving throws for enemies", "Dreaded"),
    RockSteady("-5 to stability hits received by mech","Rocksteady"),
    JungleExpert("+5 to evasion and +1 to movement points on jungle maps", "Jungle expert"),
    DesertExpert("+1 movement speed on desert maps", "Desert expert"),
    SwampExpert("+5 to stability and +1 to movement points on swamp maps", "Swamp expert"),
    SnowExpert("+5 to stability on snow maps", "Snow expert"),
    Engineer("-5 heat to mech during cooling phase", "Engineer");

    String description;
    String name;

    Perks(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
