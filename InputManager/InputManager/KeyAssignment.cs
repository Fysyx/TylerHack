using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Input;

namespace InputManager.KeyboardInput
{
    public enum KeyActionType
    {
        KeyPressed, KeyReleased, KeyDown, KeyUp
    }

    public class KeyAssignment
    {
        #region IVars
        // Key to monitor
        private Keys _key;

        // Action to be performed when key is pressed
        private KeyAction _keyAction;

        private KeyActionType _actionType;
        #endregion

        #region Properties
        // Public accessor for _key
        public Keys Key
        {
            get
            {
                return _key;
            }
        }

        public KeyActionType ActionType
        {
            get
            {
                return _actionType;
            }
        }
        #endregion

        #region Statics and Constants
        // Declaration for delegate type used
        public delegate void KeyAction(KeyboardState keyState);
        #endregion

        #region Public Methods
        public KeyAssignment(Keys key, KeyAction action, KeyActionType actionType)
        {
            _key = key;
            _keyAction = action;
            _actionType = actionType;
        }

        // Calls the action stored for this key
        public void Act()
        {
            if (_keyAction != null)
            {
                _keyAction(Keyboard.GetState());
            }
        }
        #endregion
    }
}
