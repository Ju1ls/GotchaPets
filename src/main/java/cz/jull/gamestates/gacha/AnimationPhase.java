package cz.jull.gamestates.gacha;

/**
 * Defines the specific steps of the interactive gacha roll animation.
 */
public enum AnimationPhase {
    IDLE,
    WAITING_FIRST_CLICK,
    SHAKING_BOX,
    WAITING_SECOND_CLICK,
    BOX_OPEN,
    FLASH,
    REVEAL;
}
