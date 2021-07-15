using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class GameSession : MonoBehaviour
{
    [SerializeField] int playerLives = 3;
    [SerializeField] int playerScore = 0;
    [SerializeField] TextMeshProUGUI scoreText;
    [SerializeField] TextMeshProUGUI liveText;

    private void Awake()
    {
        // Get the number of GameSession objects
        int numberGameSessions = FindObjectsOfType<GameSession>().Length;
        // If there are more than one destroy itself
        if (numberGameSessions > 1)
        {
            Destroy(gameObject);
        }
        else
        {
            // Keep the gameSession object otherwise
            DontDestroyOnLoad(gameObject);
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        // Assign text fields to relevant values
        liveText.text = playerLives.ToString();
        scoreText.text = playerScore.ToString();
    }

    // Increment score
    public void AddToScore(int points)
    {
        playerScore += points;
        scoreText.text = playerScore.ToString();
    }

    // Increase health points
    public void IncrementHealth(int amount)
    {
        playerLives += amount;
        liveText.text = playerLives.ToString();
    }

    // Load start menu and destroy gameSession object
    public void ResetGameSession()
    {
        SceneManager.LoadScene(0);
        Destroy(gameObject);
    }

    private void DecrementLife()
    {
        playerLives--;
        // Assign current scene index
        int currentSceneIndex = SceneManager.GetActiveScene().buildIndex;
        // Reload the current scene
        SceneManager.LoadScene(currentSceneIndex);
        liveText.text = playerLives.ToString();
    }

    public void ExecutePlayerDeath()
    {
        if (playerLives > 1)
        {
            // Take 1 life from player 
            DecrementLife();
        }
        else
        {
            ResetGameSession();
        }
    }
}
