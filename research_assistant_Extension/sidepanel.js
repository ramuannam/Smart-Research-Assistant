document.addEventListener('DOMContentLoaded', () => {
    chrome.storage.local.get(['researchNotes'], function(result) { //using researchNotes ad key to get the notes from browser local storage
       if (result.researchNotes) {
        document.getElementById('notes').value = result.researchNotes;
       } 
    });

    document.getElementById('summarizeBtn').addEventListener('click', summarizeText);
    document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
});


async function summarizeText() {
    try {
         //step: get the active tab, whichever the user is on
        const [tab] = await chrome.tabs.query({ active:true, currentWindow: true}); //getting the users active(current) tab
        //setp:2  get the selected text from the active tab
        const [{ result }] = await chrome.scripting.executeScript({
            target: {tabId: tab.id},
            function: () => window.getSelection().toString()
        });

        //validation
        if (!result) {
            showResult('Please select some text first');
            return;
        }
        
        //making an API call from browser
        const response = await fetch('http://localhost:8080/api/research/process', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content: result, operation: 'summarize'})  //passing the type of content we need to pass to the backend to process,(content, operation)
        });
         
        //another validation to check
        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }

        const text = await response.text();
        showResult(text.replace(/\n/g,'<br>')); //replacing the line breaks in text with line breaks in html 

    } catch (error) {
        showResult('Error: ' + error.message);
    }
}


async function saveNotes() {
    const notes = document.getElementById('notes').value;
    chrome.storage.local.set({ 'researchNotes': notes}, function() {
        alert('Notes saved successfully');
    });
}


function showResult(content) {
    document.getElementById('results').innerHTML = `<div class="result-item"><div class="result-content">${content}</div></div>`;
}