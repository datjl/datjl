using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HealthPointPickUp : MonoBehaviour
{
    [SerializeField] AudioClip healthPickUpSound;
    private int healthPoints = 1;
    private bool healthPicked = false;

    private void OnTriggerEnter2D(Collider2D other)
    {
        // Check if coin was picked (To avoid double counting score)
        if (!healthPicked)
        {
            healthPicked = true;
            // Increment health
            FindObjectOfType<GameSession>().IncrementHealth(healthPoints);
            // Execute coin picking up sound effect
            AudioSource.PlayClipAtPoint(healthPickUpSound, Camera.main.transform.position);
            // When collided with player, destroy itself
            Destroy(gameObject);
        }
    }
}
