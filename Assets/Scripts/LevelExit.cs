using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LevelExit : MonoBehaviour
{
    private float levelLoadDelay = 1.5f;
    private float levelExitSlowMotionRate = 0;

    // When player collides with Level Exit sign execute LoadNextLevel
    private void OnTriggerEnter2D(Collider2D other)
    {
        StartCoroutine(LoadNextLevel());
    }

    // Load Next Level after defined delay (2f)
    IEnumerator LoadNextLevel()
    {
        // Slow down game for (levelLoadDelay) period
        Time.timeScale = levelExitSlowMotionRate;
        yield return new WaitForSecondsRealtime(levelLoadDelay);
        // Destroy ScenePersist from current level to avoid overlapping issues
        Destroy(FindObjectOfType<ScenePersist>());
        // GO back to normal state of game speed
        Time.timeScale = 1f;
        // Get the current scene index and assign it
        var currentSceneIndex = SceneManager.GetActiveScene().buildIndex;
        // Load next scene
        SceneManager.LoadScene(currentSceneIndex + 1);
    }
}
