// $(function() {
//     alert("HI");
// });

const createNewVideoChat = async () => {
    event.preventDefault();

    const token = await getToken();
    const meetingId = await getMeetingId(token);
    const title = document.getElementsByName("title")[0].value;
    const description = document.getElementsByName("description")[0].value;
    const topicId = document.getElementsByName("topicId")[0].value;
    const languageId = document.getElementsByName("languageId")[0].value;

    console.log(token, meetingId, title, description, topicId, languageId)

    const options = {
        method: "POST",
        body: JSON.stringify({
            title,
            description,
            topicId,
            languageId,
            meetingId
        })
    }

    fetch('/start-conversation', options)
        .then((result) => {
            console.log(result);
        })
        .catch((error) => console.log(error))
}

const getToken = async () => {
    try {
        const response = await fetch(`/video/get-token`, {
            method: "GET",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
        });
        const { token } = await response.json();
        return token;
    } catch (e) {
        console.log(e);
    }
};


const getMeetingId = async (token) => {
    try {
        const VIDEOSDK_API_ENDPOINT = `video/create-meeting`;
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ token, region: "sg001" }),
        };
        return await fetch(VIDEOSDK_API_ENDPOINT, options)
            .then(async (result) => {
                const {meetingId} = await result.json();
                return meetingId;
            })
            .catch((error) => console.log("error", error));
    } catch (e) {
        console.log(e);
    }
};

/** This API is for validate the meeting id  */
/** Not require to call this API after create meeting API  */
const validateMeeting = async (token, meetingId) => {
    try {
        const VIDEOSDK_API_ENDPOINT = `/video/validate-meeting/${meetingId}`;
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ token }),
        };
        return await fetch(VIDEOSDK_API_ENDPOINT, options)
            .then(async (result) => {
                const {meetingId} = await result.json();
                return meetingId;
            })
            .catch((error) => console.log("error", error));
    } catch (e) {
        console.log(e);
    }
};

// const access_token = await getToken();
// const meetingId = await getMeetingId(access_token);
