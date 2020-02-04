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
    Cautious("-1 to morale saves,  +1 to all weapon ranges", "Cautious"),
    Brave("+1 to all morale saves", "Brave"),
    Crazy("No morale saves, always fights", "Crazy"),
    Lucky("+3%  to critical chances with weapons", "Lucky"),
    Leader("+1  to morale saves for the team", "Leader"),
    Hero("+2 to morale saves for the team", "Leader"),
    Dreaded("-1 morale saving throws for enemies", "Dreaded"),
    RockSteady("-1 to stability hits received by mech","Rocksteady"),
    JungleExpert("+5 to evasion on jungle maps", "Jungle expert"),
    DesertExpert("+1 movement speed on desert maps", "Desert expert"),
    SwampExpert("+5 to stability on swamp maps", "Swamp expert"),
    Engineer("-5 heat to mech during cooling phase", "Engineer");

    String description;
    String name;

    Perks(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
