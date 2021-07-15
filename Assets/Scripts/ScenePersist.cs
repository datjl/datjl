using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ScenePersist : MonoBehaviour
{
    int startingSceneIndex;

    private void Awake()
    {
        // Get the number of ScenePersist objects
        int numberScenePersists = FindObjectsOfType<ScenePersist>().Length;
        if (numberScenePersists > 1)
        {
            // Destroy itself if there are more than one objects
            Destroy(gameObject);
        }
        else
        {
            DontDestroyOnLoad(gameObject);
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        // Assign starting scene index
        startingSceneIndex = SceneManager.GetActiveScene().buildIndex;
    }

    // Update is called once per frame
    void Update()
    {
        // Assign current scene index
        int currentSceneIndex = SceneManager.GetActiveScene().buildIndex;
        // If it is not matching with starting scene, destroy itself
        if (currentSceneIndex != startingSceneIndex)
        {
            Destroy(gameObject);
        }
    }
}
