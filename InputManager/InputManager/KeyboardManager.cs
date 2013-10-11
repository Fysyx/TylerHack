using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework;

namespace InputManager.KeyboardInput
{
    public class KeyboardManager
    {
        #region IVars
        // List of mapped keys, also dies to the flood
        private List<KeyAssignment> captainKeys;

        private KeyboardState previousKeyState;

        // IVar for instance (singleton)
        private static KeyboardManager _instance;
        #endregion

        #region Properties
        // Getter/Lazy Instantion for signleton instance
        public static KeyboardManager Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new KeyboardManager();
                }

                return _instance;
            }
        }

        public List<KeyAssignment> KeyMapping
        {
            get { return captainKeys; }
            set { captainKeys = value; }
        }
        #endregion

        #region Public Methods
        /// <summary>
        /// Adds the given KeyAssignment to the list
        /// of KeyAssignments to be tracked
        /// </summary>
        public void MapKey(KeyAssignment keyAssign)
        {
            captainKeys.Add(keyAssign);
        }

        /// <summary>
        /// Creates a KeyAssignment with the given information
        /// and calls MapKey(KeyAssignment keyAssign)
        /// </summary>
        public void MapKey(Keys key, KeyAssignment.KeyAction action, KeyActionType actionType)
        {
            MapKey(new KeyAssignment(key, action, actionType));
        }

        /// <summary>
        /// Iterates through each KeyAssignment in the list of 
        /// keys, checks the action type on that assignment and 
        /// then checks if that specific action type has occured 
        /// on that key. If the action has occured, the delegate
        /// stored in each assignment is activated.
        /// </summary>
        /// <param name="gameTime"></param>
        public void Update(GameTime gameTime)
        {
            KeyboardState currentKeyState = Keyboard.GetState();

            foreach (KeyAssignment keyAssign in captainKeys)
            {
                switch (keyAssign.ActionType)
                {
                    case (KeyActionType.KeyDown):
                        if (currentKeyState.IsKeyDown(keyAssign.Key))
                        {
                            keyAssign.Act();
                        }
                        break;
                    case (KeyActionType.KeyUp):
                        if (currentKeyState.IsKeyUp(keyAssign.Key))
                        {
                            keyAssign.Act();
                        }
                        break;
                    case (KeyActionType.KeyPressed):
                        if (previousKeyState.IsKeyUp(keyAssign.Key) &&
                            currentKeyState.IsKeyDown(keyAssign.Key))
                        {
                            keyAssign.Act();
                        }
                        break;
                    case (KeyActionType.KeyReleased):
                        if (previousKeyState.IsKeyDown(keyAssign.Key) &&
                            currentKeyState.IsKeyUp(keyAssign.Key))
                        {
                            keyAssign.Act();
                        }
                        break;
                    default:
                        break;
                }
            }
            previousKeyState = currentKeyState;
        }

        public void ClearMappings()
        {
            captainKeys.Clear();
        }
        #endregion

        #region Private Methods
        private KeyboardManager()
        {
            captainKeys = new List<KeyAssignment>();
            previousKeyState = Keyboard.GetState();
        }
        #endregion
    }
}
