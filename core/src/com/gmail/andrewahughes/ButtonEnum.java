package com.gmail.andrewahughes;

public class ButtonEnum {
    /*i need an enum for every button. when buttons are created they are passed a unique enum so that button can
    reference itself. the enum will also be used to get that button from the button array
    * it's important to add the buttons to the array at the enum index position so that if the buttons are added
    * out of order then we will get an error message and we can fix it, otherwise there would be no error but when
    * we try and get the button with the enum as the index then it will get the wrong button
    */
    enum Tri {
        TITLEEXIT                   (0),
        TITLENEXTSTAGE              (1),

        /*buttons for a new stage must begin again at value 0*/
        OPTIONSNEXTSTAGE            (0),

        MATCHMAKINGNEXTSTAGE        (0),

        DEALNEXTSTAGE               (0),
        DEALBEGINDEAL               (1),
        DEALINCREASEPAR             (2),
        DEALDECREASEPAR             (3),
        DEALCONFIRMPAR              (4),

        TRIDENTBUILDINGPLAYERTRIDENTARRAY1  (0),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY2  (1),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY3  (2),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY4  (3),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY5  (4),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY6  (5),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY7  (6),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY8  (7),
        TRIDENTBUILDINGPLAYERTRIDENTARRAY9  (8),
        TRIDENTBUILDINGAUTOBUILD            (9),
        TRIDENTBUILDINGSPEARMED             (10),
        TRIDENTBUILDINGSPEARLOW             (11),
        TRIDENTBUILDINGBIDENTMED            (12),
        TRIDENTBUILDINGBIDENTLOW            (13),
        TRIDENTBUILDINGTRIDENTHI            (14),
        TRIDENTBUILDINGTRIDENTMED           (15),
        TRIDENTBUILDINGTRIDENTLOW           (16),
        TRIDENTBUILDINGSUIT0NATURE          (17),
        TRIDENTBUILDINGSUIT1LIGHT           (18),
        TRIDENTBUILDINGSUIT2DEMON           (19),
        TRIDENTBUILDINGSUIT3DARK            (20),
        TRIDENTBUILDINGSUIT4ANY             (21),
        TRIDENTBUILDINGAUTOBUILDROTATE      (22),
        TRIDENTBUILDINGAUTOBUILDFLIP        (23),
        TRIDENTBUILDINGAUTOBUILDCONFIRM     (24),
        TRIDENTBUILDINGNEXTSTAGE            (25),
        TRIDENTBUILDINGWILDCARD             (26),
        TRIDENTBUILDINGWILDCARD0NATURE      (27),
        TRIDENTBUILDINGWILDCARD1LIGHT       (28),
        TRIDENTBUILDINGWILDCARD2DEMON       (29),
        TRIDENTBUILDINGWILDCARD3DARK        (30),
        TRIDENTBUILDINGWILDCARD4NONE        (31),

        GAMEBOARD0                  (0),
        GAMEBOARD1                  (1),
        GAMEBOARD2                  (2),
        GAMEBOARD3                  (3),
        GAMEBOARD4                  (4),
        GAMEBOARD5                  (5),
        GAMEBOARD6                  (6),
        GAMEBOARD7                  (7),
        GAMEBOARD8                  (8),
        GAMEBOARD9                  (9),
        GAMEBOARD10                 (10),
        GAMEBOARD11                 (11),
        GAMEBOARD12                 (12),
        GAMEBOARD13                 (13),
        GAMEBOARD14                 (14),
        GAMEBOARD15                 (15),
        GAMETRIHAND0                (16),
        GAMETRIHAND1                (17),
        GAMETRIHAND2                (18),
        GAMETRIHAND3                (19),
        GAMETRIHAND4                (20),
        GAMETRIHAND5                (21),
        GAMETRIHAND6                (22),
        GAMETRIHAND7                (23),
        GAMETRIPREGAMECARD          (24),
        GAMETRIPOSTGAMECARD         (25),
        GAMEPLACEROTATE             (26),
        GAMEPLACEFLIP               (27),
        GAMEPLACECONFIRM            (28),

        GAMENEXTSTAGE               (29),

        GAMEOVERNEXTSTAGE           (0);

        final int value;
        Tri(int i) {
            this.value= i;
        }
    }
    static int card = 0;
    enum Card {
        TRIDENTBUILDING0,
        TRIDENTBUILDING1,
        TRIDENTBUILDING2,
        TRIDENTBUILDING3,
        TRIDENTBUILDING4,
        TRIDENTBUILDING5,
        TRIDENTBUILDING6,
        TRIDENTBUILDING7,
        TRIDENTBUILDING8,
        TRIDENTBUILDING9,
        TRIDENTBUILDING10,
        TRIDENTBUILDING11,
        TRIDENTBUILDING12,
        TRIDENTBUILDING13,
        TRIDENTBUILDING14,
        TRIDENTBUILDING15,
        TRIDENTBUILDING16,
        TRIDENTBUILDING17,
        TRIDENTBUILDING18,
        TRIDENTBUILDING19,
        TRIDENTBUILDING20,
        TRIDENTBUILDING21,
        TRIDENTBUILDING22,
        TRIDENTBUILDING23,
        TRIDENTBUILDING24,
        TRIDENTBUILDING25,
        TRIDENTBUILDING26,
        TRIDENTBUILDING27,
        TRIDENTBUILDING28,
        TRIDENTBUILDING29,
        TRIDENTBUILDING30,
        TRIDENTBUILDING31,
        TRIDENTBUILDING32,
        TRIDENTBUILDING33,
        TRIDENTBUILDING34,
        TRIDENTBUILDING35,
        TRIDENTBUILDING36,
        TRIDENTBUILDING37,
        TRIDENTBUILDING38,
        TRIDENTBUILDING39,
        TRIDENTBUILDING40,
        TRIDENTBUILDING41,
        TRIDENTBUILDING42,
        TRIDENTBUILDING43,
        TRIDENTBUILDING44,
        TRIDENTBUILDING45,
        TRIDENTBUILDING46,
        TRIDENTBUILDING47,
        TRIDENTBUILDING48,
        TRIDENTBUILDING49,
        TRIDENTBUILDING50,
        TRIDENTBUILDING51,
        TRIHANDCARD0,
        TRIHANDCARD1,
        TRIHANDCARD2,
        TRIHANDCARD3,
        TRIHANDCARD4,
        TRIHANDCARD5,
        TRIHANDCARD6,
        TRIHANDCARD7,
        TRIHANDCARD8,
        TRIHANDCARD9,
        TRIHANDCARD10,
        TRIHANDCARD11,
        TRIHANDCARD12,
        TRIHANDCARD13,
        TRIHANDCARD14,
        TRIHANDCARD15,
        TRIHANDCARD16,
        TRIHANDCARD17,
        TRIHANDCARD18,
        TRIHANDCARD19,
        TRIHANDCARD20,
        TRIHANDCARD21,
        TRIHANDCARD22,
        TRIHANDCARD23,
        TRIHANDCARD24,
        TRIHANDCARD25;
        int value;
        Card( ) {
            this.value =card++;
        }
    }


}
