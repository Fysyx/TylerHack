using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace InputManager.MouseInput
{
    public class MouseButtonManager
    {
        #region IVars
        // List of mapped buttons, also dies to the flood
        private List<MouseButtonAssignment> captainButtons;

        private MouseState previousMouseState;

        // IVar for instance (singleton)
        private static MouseButtonManager _instance;
        #endregion

        #region Properties
        // Getter/Lazy Instantion for signleton instance
        public static MouseButtonManager Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new MouseButtonManager();
                }

                return _instance;
            }
        }
        #endregion

        #region Public Methods
        /// <summary>
        /// Adds the given MouseButtonAssignment to the list
        /// of MouesButtonAssignments to be tracked
        /// </summary>
        public void MapButton(MouseButtonAssignment mouseButtonAssign)
        {
            captainButtons.Add(mouseButtonAssign);
        }

        /// <summary>
        /// Creates a MouseButtonAssignment with the given information
        /// and calls MapKey(MouseButtonAssignment mouseButtonAssign)
        /// </summary>
        public void MapButton(MouseButtons button, MouseButtonAssignment.MouseButtonAction action, MouseButtonActionType actionType)
        {
            MapButton(new MouseButtonAssignment(button, action, actionType));
        }

        /// <summary>
        /// Iterates through each MouseButtonAssignment in the list of 
        /// buttons, checks the action type on that assignment and 
        /// then checks if that specific action type has occured 
        /// on that button. If the action has occured, the stored delegate is activated.
        /// </summary>
        public void Update(GameTime gameTime)
        {
            MouseState currentMouseState = Mouse.GetState();

            foreach (MouseButtonAssignment mouseButtonAssign in captainButtons)
            {
                switch(mouseButtonAssign.Button)
                {
                    case (MouseButtons.Left):
                        switch (mouseButtonAssign.ActionType)
                        {
                            case (MouseButtonActionType.ButtonClickPress):
                                if (previousMouseState.LeftButton == ButtonState.Released &&
                                    currentMouseState.LeftButton == ButtonState.Pressed)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            case (MouseButtonActionType.ButtonClickRelease):
                                if (previousMouseState.LeftButton == ButtonState.Pressed &&
                                    currentMouseState.LeftButton == ButtonState.Released)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            case (MouseButtonActionType.ButtonPressed):
                                if (currentMouseState.LeftButton == ButtonState.Pressed)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            case (MouseButtonActionType.ButtonReleased):
                                if (currentMouseState.LeftButton == ButtonState.Released)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case (MouseButtons.Right):
                        switch (mouseButtonAssign.ActionType)
                        {
                            case (MouseButtonActionType.ButtonClickPress):
                                if (previousMouseState.RightButton == ButtonState.Released &&
                                    currentMouseState.RightButton == ButtonState.Pressed)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            case (MouseButtonActionType.ButtonClickRelease):
                                if (previousMouseState.RightButton == ButtonState.Pressed &&
                                    currentMouseState.RightButton == ButtonState.Released)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            case (MouseButtonActionType.ButtonPressed):
                                if (currentMouseState.RightButton == ButtonState.Pressed)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            case (MouseButtonActionType.ButtonReleased):
                                if (currentMouseState.RightButton == ButtonState.Released)
                                {
                                    mouseButtonAssign.Act();
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                
                }
            }
            previousMouseState = currentMouseState;
        }

        public void ClearMappings()
        {
            captainButtons.Clear();
        }
        #endregion

        #region Private Methods
        private MouseButtonManager()
        {
            captainButtons = new List<MouseButtonAssignment>();
            previousMouseState = Mouse.GetState();
        }
        #endregion
    }
}
