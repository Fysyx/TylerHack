using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace InputManager.MouseInput
{
    public enum MouseButtonActionType
    {
        ButtonPressed, ButtonReleased, ButtonClickPress, ButtonClickRelease
    }

    public enum MouseButtons {
        Left, Right
    }

    public class MouseButtonAssignment
    {
        #region IVars
        // Key to monitor
        private MouseButtons _button;

        // Action to be performed when key is pressed
        private MouseButtonAction _action;

        private MouseButtonActionType _actionType;
        #endregion

        #region Properties
        // Public accessor for _key
        public MouseButtons Button
        {
            get
            {
                return _button;
            }
        }

        public MouseButtonActionType ActionType
        {
            get
            {
                return _actionType;
            }
        }
        #endregion

        #region Statics and Constants
        // Declaration for delegate type used
        public delegate void MouseButtonAction();
        #endregion

        #region Public Methods
        public MouseButtonAssignment(MouseButtons button, MouseButtonAction action, MouseButtonActionType actionType)
        {
            _button = button;
            _action = action;
            _actionType = actionType;
        }

        // Calls the action stored for this key
        public void Act()
        {
            if (_action != null)
            {
                _action();
            }
        }
        #endregion
    }
}
